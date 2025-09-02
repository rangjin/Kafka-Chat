package com.rangjin.chatapi.adapter.`in`.api.channel.dto.response

import com.fasterxml.jackson.annotation.JsonFilter
import com.rangjin.chatapi.adapter.`in`.api.user.dto.response.UserSummaryResponse
import java.time.LocalDateTime

@JsonFilter("FieldFilter")
data class ChannelDetailResponse(

    val id: Long? = null,

    val name: String,

    val members: List<UserSummaryResponse> = emptyList(),

    val createdAt: LocalDateTime? = null,

    val updatedAt: LocalDateTime? = null

)