package com.rangjin.chatapi.adapter.`in`.api.channel.dto.response

import com.rangjin.chatapi.adapter.`in`.api.user.dto.response.UserWithoutPasswordResponse
import java.time.LocalDateTime

data class ChannelResponse(

    val id: Long? = null,

    val name: String,

    val members: List<UserWithoutPasswordResponse> = emptyList(),

    val createdAt: LocalDateTime? = null,

    val updatedAt: LocalDateTime? = null

)