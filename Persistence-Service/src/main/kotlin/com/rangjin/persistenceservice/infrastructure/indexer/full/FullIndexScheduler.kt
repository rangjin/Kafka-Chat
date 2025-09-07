package com.rangjin.persistenceservice.infrastructure.indexer.full

import com.rangjin.persistenceservice.infrastructure.indexer.config.IndexProps
import com.rangjin.persistenceservice.infrastructure.indexer.config.IndexSource
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class FullIndexScheduler(

    private val props: IndexProps,

    private val indexer: GenericFullIndexer,

    private val sources: List<IndexSource<*, *>>

) {

    @Scheduled(cron = "0 0 3 * * *")
    fun runAll() {
        props.domains.forEach { (name, cfg) ->
            val src =
                sources.firstOrNull {
                    it.name == name
                }

            if (src == null) {
                println("[REINDEX][WARN] no source bean for '$name' — skip")
                return@forEach
            }

            runTyped(cfg, src)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun runTyped(
        cfg: IndexProps.DomainIndexProps,
        src: IndexSource<*, *>
    ) {
        // 합성 경계에서 단 한 번만 다운캐스트 (구현체는 제네릭 타입 안전)
        indexer.run(
            cfg,
            src as IndexSource<Any, Any>
        )
    }

}