package com.rangjin.chatapiindexer.application.port.out

interface UpsertIndexPort {

    fun <T : Any, D : Any> upsert(
        alias: String,
        domain: T,
        mapper: DocMapper<T, D>,
    )

}