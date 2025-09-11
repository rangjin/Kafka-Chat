package com.rangjin.chatapiindexer.application.port.out

interface BulkIndexPort {

    fun <T : Any, D : Any> saveAll(
        index: String,
        domains: List<T>,
        mapper: DocMapper<T, D>
    )

}