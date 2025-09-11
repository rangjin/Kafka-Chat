package com.rangjin.chatapiindexer.infrastructure.search.adapter

import com.rangjin.chatapiindexer.application.port.out.BulkIndexPort
import com.rangjin.chatapiindexer.application.port.out.DocMapper
import com.rangjin.chatapiindexer.application.port.out.UpsertIndexPort
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates
import org.springframework.data.elasticsearch.core.query.IndexQuery
import org.springframework.data.elasticsearch.core.query.UpdateQuery
import org.springframework.stereotype.Component

@Component
class EsIndexAdapter(

    private val ops: ElasticsearchOperations,

    private val converter: ElasticsearchConverter

): BulkIndexPort, UpsertIndexPort {

    override fun <E : Any, D : Any> saveAll(
        index: String,
        domains: List<E>,
        mapper: DocMapper<E, D>
    ) {
        if (domains.isEmpty()) return

        val docs = domains.map {
            mapper.toDoc(it)
        }

        val queries = docs.map {
            IndexQuery.builder()
                .withObject(it)
                .withRouting(mapper.getRouting(it))
                .build()
        }

        ops.bulkIndex(queries, IndexCoordinates.of(index))
    }

    override fun <T : Any, D : Any> upsert(
        alias: String,
        domain: T,
        mapper: DocMapper<T, D>,
    ) {
        val doc = mapper.toDoc(domain)
        val id = mapper.getId(doc)

        val query = UpdateQuery.builder(id)
            .withDocument(
                converter.mapObject(doc)
            ).withDocAsUpsert(true)
            .withRouting(mapper.getRouting(doc))
            .build()

        ops.update(query, IndexCoordinates.of(alias))
    }

}