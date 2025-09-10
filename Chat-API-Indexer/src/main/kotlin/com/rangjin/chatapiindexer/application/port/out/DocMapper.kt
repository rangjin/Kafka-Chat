package com.rangjin.chatapiindexer.application.port.out

import kotlin.reflect.KClass

interface DocMapper<T : Any, D : Any> {

    val domain: String

    val docClass: KClass<D>

    fun toDoc(domain: T): D

}