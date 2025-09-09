package com.rangjin.chatapiindexer.infrastructure.indexer.full

import com.rangjin.chatapiindexer.infrastructure.indexer.config.IndexProps
import com.rangjin.chatapiindexer.infrastructure.indexer.config.IndexSource
import com.rangjin.chatapiindexer.infrastructure.indexer.util.BlueGreenPlanner
import com.rangjin.chatapiindexer.infrastructure.indexer.util.BulkWriter
import com.rangjin.chatapiindexer.infrastructure.indexer.util.EsAdminClient
import com.rangjin.chatapiindexer.infrastructure.indexer.util.IndexCreator
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

@Component
class GenericFullIndexer(

    private val planner: BlueGreenPlanner,

    private val indexCreator: IndexCreator,

    private val es: EsAdminClient,

    private val bulk: BulkWriter

) {

    fun <T : Any, D : Any> run(
        props: IndexProps.DomainIndexProps,
        source: IndexSource<T, D>
    ) {
        val plan = planner.plan(blue = props.blue, green = props.green, alias = props.alias)
        val target = plan.standby
        println("[REINDEX] ${source.name} active=${plan.active ?: "(none)"} standby=$target")

        // standby 인덱스(대량 색인 세팅) 재생성
        indexCreator.recreate(
            index = target,
            entityClass = source.docClass.java,
            settings = mapOf("index" to mapOf("number_of_replicas" to 0, "refresh_interval" to "-1"))
        )

        // 전량 색인
        var after = 0L
        val page = PageRequest.of(0, props.batchSize)
        while (true) {
            val rows = source.fetchPage(after, page)
            if (rows.isEmpty()) break

            bulk.saveAll(target, rows.map(source::toDoc))

            after = source.entityId(rows.last())
            println("[REINDEX] ${source.name} upto=$after (batch=${rows.size})")
        }
        bulk.refresh(target)

        // 운영 세팅 원복 + alias 스위치
        es.putIndexSettings(target, mapOf("index" to mapOf("number_of_replicas" to 1, "refresh_interval" to "1s")))
        es.switchAlias(alias = props.alias, removeIndex = plan.active, addIndex = target)
        println("[REINDEX] ${source.name} alias ${props.alias} -> $target")
    }

}