package com.rangjin.chatapi.presentation.api.channel.dto.response

import com.rangjin.chatapi.domain.channel.Channel
import java.time.LocalDateTime

data class ChannelDetailResponse(

    val id: Long? = null,

    val name: String,

    val createdAt: LocalDateTime? = null,

    val updatedAt: LocalDateTime? = null

) {

    companion object {
        fun from(channel: Channel): ChannelDetailResponse =
            ChannelDetailResponse(
                id = channel.id,
                name = channel.name,
                createdAt = channel.createdAt,
                updatedAt = channel.updatedAt
            )
    }

}