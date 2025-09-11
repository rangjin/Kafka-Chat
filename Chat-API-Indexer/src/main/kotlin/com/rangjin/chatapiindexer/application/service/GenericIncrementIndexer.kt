package com.rangjin.chatapiindexer.application.service

import com.rangjin.chatapiindexer.application.port.`in`.IncrementIndexer
import com.rangjin.chatapiindexer.application.port.out.DocMapper
import com.rangjin.chatapiindexer.application.port.out.IndexLifecyclePort
import com.rangjin.chatapiindexer.application.port.out.UpsertIndexPort
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class GenericIncrementIndexer(

    mappers: List<DocMapper<*, *>>,

    private val upsertIndexPort: UpsertIndexPort,

    private val lifecycle: IndexLifecyclePort

): IncrementIndexer {

    private val registry: Map<KClass<*>, DocMapper<*, *>> =
        mappers.associateBy { it.domainClass }

    override fun <T : Any> upsert(domain: T, routing: String) {
        val mapperAny = registry[domain::class]
            ?: error("Mapper not registered for ${domain::class}")

        @Suppress("UNCHECKED_CAST")
        val mapper = mapperAny as DocMapper<T, *>

        // 전체 색인 진행 중이면 blue, green에 double-write
        val plan = lifecycle.fullIndexInProgress()
        if (plan != null && plan.alias == mapper.domain) {
            if (plan.active != null)
                upsertIndexPort.upsert(
                    alias = plan.active,
                    domain = domain,
                    mapper = mapper
                )
            upsertIndexPort.upsert(
                alias = plan.standby,
                domain = domain,
                mapper = mapper
            )
        } else {
            upsertIndexPort.upsert(
                alias = mapper.domain,
                domain = domain,
                mapper = mapper
            )
        }
    }

}