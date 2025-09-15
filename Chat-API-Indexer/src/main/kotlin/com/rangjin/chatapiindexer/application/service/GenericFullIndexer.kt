package com.rangjin.chatapiindexer.application.service

import com.rangjin.chatapiindexer.application.config.IndexProps
import com.rangjin.chatapiindexer.application.port.`in`.FullIndexer
import com.rangjin.chatapiindexer.application.port.out.BulkIndexPort
import com.rangjin.chatapiindexer.application.port.out.DocMapper
import com.rangjin.chatapiindexer.application.port.out.IndexLifecyclePort
import com.rangjin.chatapiindexer.application.port.out.PageSource
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

@Component
class GenericFullIndexer(

    private val indexProps: IndexProps,

    private val lifecycle: IndexLifecyclePort,

    private val bulkIndexPort: BulkIndexPort,

    private val sources: List<PageSource<*>>,

    private val mappers: List<DocMapper<*, *>>

) : FullIndexer {

    override fun runAll() {
        indexProps.domains.forEach { (name, cfg) ->
            try {
                val source = castSource<Any>(
                    sources.firstOrNull { it.name == name } ?: return@forEach
                )
                val mapper = castMapper<Any, Any>(
                    mappers.firstOrNull { it.domain == name } ?: return@forEach
                )

                val plan = lifecycle.plan(cfg.blue, cfg.green, cfg.alias)

                lifecycle.fullIndexStart(plan)

                lifecycle.recreateIndex(
                    index = plan.standby,
                    mapper = mapper
                )

                var after = 0L
                val page = PageRequest.of(0, cfg.batchSize)
                while (true) {
                    val rows = source.fetchPage(after, page)
                    if (rows.isEmpty()) break

                    bulkIndexPort.saveAll(
                        index = plan.standby,
                        domains = rows,
                        mapper = mapper
                    )

                    after = source.entityId(rows.last())
                }

                lifecycle.refresh(plan.standby)
                lifecycle.putIndexSettings(plan.standby)
                lifecycle.switchAlias(alias = plan.alias, removeIndex = plan.active, addIndex = plan.standby)
            } finally {
                lifecycle.fullIndexFinish()
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <E : Any> castSource(src: PageSource<*>): PageSource<E> =
        src as PageSource<E>

    @Suppress("UNCHECKED_CAST")
    private fun <E : Any, D : Any> castMapper(mapper: DocMapper<*, *>): DocMapper<E, D> =
        mapper as DocMapper<E, D>

}