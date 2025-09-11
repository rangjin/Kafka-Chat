package com.rangjin.chatapiindexer.application.port.out

import kotlin.reflect.KClass

interface DocMapper<T : Any, D : Any> {

    val domain: String

    val domainClass: KClass<T>

    val docClass: KClass<D>

    fun toDoc(domain: T): D

    fun getId(doc: D): String

    fun getRouting(doc: D): String

}