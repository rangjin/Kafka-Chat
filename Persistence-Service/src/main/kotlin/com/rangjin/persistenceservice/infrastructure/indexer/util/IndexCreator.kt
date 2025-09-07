package com.rangjin.persistenceservice.infrastructure.indexer.util

import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates
import org.springframework.stereotype.Component

@Component
class IndexCreator(

    private val ops: ElasticsearchOperations

) {

    fun recreate(
        index: String,
        entityClass: Class<*>,
        settings: Map<String, Any>
    ) {
        val io = ops.indexOps(IndexCoordinates.of(index))
        if (io.exists()) io.delete()
        io.create(settings, io.createMapping(entityClass))
        io.refresh()
    }

}