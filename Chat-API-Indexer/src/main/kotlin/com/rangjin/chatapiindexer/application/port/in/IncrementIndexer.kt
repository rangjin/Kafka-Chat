package com.rangjin.chatapiindexer.application.port.`in`

interface IncrementIndexer {

    fun <T: Any> upsert(domain: T, routing: String)

}