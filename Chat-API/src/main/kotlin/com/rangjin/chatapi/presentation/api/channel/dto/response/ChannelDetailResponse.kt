package com.rangjin.chatapi.presentation.api.channel.dto.response

import com.fasterxml.jackson.annotation.JsonFilter
import com.rangjin.chatapi.domain.channel.Channel
import com.rangjin.chatapi.domain.user.User
import com.rangjin.chatapi.presentation.api.user.dto.response.UserSummaryResponse
import java.time.LocalDateTime

@JsonFilter("FieldFilter")
data class ChannelDetailResponse(

    val id: Long? = null,

    val name: String,

    val members: List<User> = emptyList(),

    val createdAt: LocalDateTime? = null,

    val updatedAt: LocalDateTime? = null

) {

    companion object {
        fun from(channel: Channel): ChannelDetailResponse =
            ChannelDetailResponse(
                id = channel.id,
                name = channel.name,
                members = channel.members.map { it.user },
                createdAt = channel.createdAt,
                updatedAt = channel.updatedAt
            )
    }

}