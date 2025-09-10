package com.rangjin.chatapiindexer.infrastructure.search.adapter

import com.rangjin.chatapiindexer.application.port.out.BulkIndexPort
import com.rangjin.chatapiindexer.application.port.out.DocMapper
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder
import org.springframework.stereotype.Component

@Component
class EsBulkIndexAdapter(

    private val ops: ElasticsearchOperations

) : BulkIndexPort {

    override fun <E : Any, D : Any> saveAll(
        index: String,
        rows: Collection<E>,
        mapper: DocMapper<E, D>
    ) {
        if (rows.isEmpty()) return

        val docs: List<D> = rows.map { mapper.toDoc(it) }

        val queries =
            docs.map {
                IndexQueryBuilder()
                    .withObject(it)
                    .build()
            }

        ops.bulkIndex(queries, IndexCoordinates.of(index))
    }


}