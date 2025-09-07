package com.rangjin.persistenceservice.infrastructure.indexer.util

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component

@Component
class EsAdminClient(

    builder: RestTemplateBuilder,

    @Value("\${spring.elasticsearch.uris}")
    private val esUri: String,

    private val objectMapper: ObjectMapper

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

    fun putIndexSettings(index: String, s: Map<String, Any>) =
        rest.exchange(
            "$esUri/$index/_settings",
            HttpMethod.PUT,
            HttpEntity(s),
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

}