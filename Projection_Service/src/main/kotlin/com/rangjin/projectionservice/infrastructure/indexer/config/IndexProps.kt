package com.rangjin.projectionservice.infrastructure.indexer.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "indexer")
data class IndexProps(

    val domains: Map<String, DomainIndexProps> = emptyMap()

) {

    data class DomainIndexProps(

        val alias: String,

        val blue: String,

        val green: String,

        val batchSize: Int = 1000

    )

}
