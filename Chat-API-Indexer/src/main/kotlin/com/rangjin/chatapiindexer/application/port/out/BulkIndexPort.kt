package com.rangjin.chatapiindexer.application.port.out

interface BulkIndexPort {

    fun <E : Any, D : Any> saveAll(
        index: String,
        rows: Collection<E>,
        mapper: DocMapper<E, D>
    )

}