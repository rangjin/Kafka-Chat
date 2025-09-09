package com.rangjin.chatapi.domain.channel

import java.time.LocalDateTime

data class ChannelEvent<T>(

    val aggregateId: String,

    val type: ChannelEventType,

    val payload: T,

    val timestamp: LocalDateTime = LocalDateTime.now()

)