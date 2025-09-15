package com.rangjin.chatapi.domain.channel

import java.time.LocalDateTime

data class Channel(

    val id: Long? = null,

    val name: String,

    var lastSeq: Long = 0,

    val createdAt: LocalDateTime? = null,

    val updatedAt: LocalDateTime? = null

) {

    fun addLastSeq() = lastSeq++

}