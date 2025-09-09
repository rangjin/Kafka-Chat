package com.rangjin.chatapi.application.event

import java.time.LocalDateTime

data class DomainEvent<T>(

    val aggregateId: String,

    val className: String,

    val type: DomainEventType,

    val payload: T,

    val timestamp: LocalDateTime = LocalDateTime.now()

)