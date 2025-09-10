package com.rangjin.chatapiindexer.presentation.messaging

data class EventEnvelope<T>(

    val type: String,

    val payload: T,

    val className: String,

    val timestamp: String,

    val aggregatedId: String

)