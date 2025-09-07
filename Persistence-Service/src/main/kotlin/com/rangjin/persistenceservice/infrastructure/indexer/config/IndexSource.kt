package com.rangjin.persistenceservice.infrastructure.indexer.config

import org.springframework.data.domain.Pageable
import kotlin.reflect.KClass

interface IndexSource<T : Any, D : Any> {

    val name: String

    val docClass: KClass<D>

    fun fetchPage(afterId: Long, pageable: Pageable): List<T>

    fun entityId(entity: T): Long

    fun toDoc(entity: T): D

}