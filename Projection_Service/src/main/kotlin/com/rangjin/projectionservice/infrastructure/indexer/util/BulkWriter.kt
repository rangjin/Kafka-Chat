package com.rangjin.projectionservice.infrastructure.indexer.util

import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder
import org.springframework.stereotype.Component

@Component
class BulkWriter(

    private val ops: ElasticsearchOperations

) {

    fun <T : Any> saveAll(
        index: String,
        docs: Collection<T>
    ) {
        if (docs.isEmpty()) return

        val queries =
            docs.map {
                IndexQueryBuilder()
                    .withObject(it)
                    .build()
            }

        ops.bulkIndex(queries, IndexCoordinates.of(index))
    }

    fun refresh(index: String) =
        ops.indexOps(IndexCoordinates.of(index)).refresh()

}