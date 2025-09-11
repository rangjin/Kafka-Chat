package com.rangjin.chatapiindexer.presentation.messaging

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.JsonNode

@JsonIgnoreProperties(ignoreUnknown = true)
data class EventEnvelope(

    val type: String,

    val payload: JsonNode,

    val className: String,

    val timestamp: String,

    val aggregateId: String

)