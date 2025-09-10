package com.rangjin.chatapiindexer.application.port.out

import org.springframework.data.domain.Pageable

interface PageSource<T> {

    val name: String

    fun fetchPage(id: Long, pageable: Pageable): List<T>

    fun entityId(domain: T): Long

}