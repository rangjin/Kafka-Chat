package com.rangjin.chatapi.domain.channel.port.`in`.command

data class CreateChannelCommand (

    val userId: Long,

    val name: String

) {
}