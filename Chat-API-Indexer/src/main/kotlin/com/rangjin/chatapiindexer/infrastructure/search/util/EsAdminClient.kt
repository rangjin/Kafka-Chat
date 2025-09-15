package com.rangjin.chatapiindexer.infrastructure.search.util

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component

@Component
class EsAdminClient(

    builder: RestTemplateBuilder,

    @Value("\${spring.elasticsearch.uris}")
    private val esUri: String,

    private val objectMapper: ObjectMapper,

    private val ops: ElasticsearchOperations

) {

    private val rest = builder.build()

    fun getAliasTargets(alias: String): List<String> =
        try {
            objectMapper.readTree(
                rest.getForObject(
                    "$esUri/_cat/aliases/$alias?format=json",
                    String::class.java
                )!!
            ).mapNotNull {
                it.get("index")?.asText()
            }
        } catch (_: Exception) {
            emptyList()
        }

    fun recreate(
        index: String,
        entityClass: Class<*>,
    ) {
        val io = ops.indexOps(IndexCoordinates.of(index))
        if (io.exists()) io.delete()

        val settings = mapOf(
            "index" to mapOf(
                "number_of_replicas" to 0,
                "refresh_interval" to "-1"
            ),
            "analysis" to mapOf(
                "analyzer" to mapOf(
                    // 한국어 형태소 분석기
                    "nori" to mapOf("type" to "nori"),

                    // ngram 분석기 (부분 문자열 검색)
                    "korean_ngram" to mapOf(
                        "type" to "custom",
                        "tokenizer" to "ngram_tokenizer"
                    )
                ),
                "tokenizer" to mapOf(
                    "ngram_tokenizer" to mapOf(
                        "type" to "ngram",
                        "min_gram" to 2,
                        "max_gram" to 3,
                        "token_chars" to listOf("letter", "digit")
                    )
                )
            )
        )

        io.create(settings, io.createMapping(entityClass))
        io.refresh()
    }

    fun putIndexSettings(index: String) =
        rest.exchange(
            "$esUri/$index/_settings",
            HttpMethod.PUT,
            HttpEntity(mapOf(
                "index" to mapOf(
                    "number_of_replicas" to 1,
                    "refresh_interval" to "1s"
                )
            )),
            String::class.java
        )

    fun switchAlias(alias: String, removeIndex: String?, addIndex: String) {
        val actions = mutableListOf<Map<String, Any>>()

        if (removeIndex != null)
            actions += mapOf(
                "remove" to mapOf(
                    "index" to removeIndex,
                    "alias" to alias
                )
            )

        actions += mapOf(
            "add" to mapOf(
                "index" to addIndex,
                "alias" to alias
            )
        )

        rest.postForEntity(
            "$esUri/_aliases",
            mapOf("actions" to actions),
            String::class.java
        )
    }

    fun refresh(index: String) {
        ops.indexOps(IndexCoordinates.of(index)).refresh()
    }

}