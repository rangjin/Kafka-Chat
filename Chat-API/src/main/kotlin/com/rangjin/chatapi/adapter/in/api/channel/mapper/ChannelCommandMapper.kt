package com.rangjin.chatapi.adapter.`in`.api.channel.mapper

import com.rangjin.chatapi.adapter.`in`.api.channel.dto.request.CreateChannelRequest
import com.rangjin.chatapi.adapter.`in`.api.channel.dto.response.ChannelDetailResponse
import com.rangjin.chatapi.adapter.`in`.api.user.mapper.toUserWithoutPasswordResponse
import com.rangjin.chatapi.domain.channel.model.Channel
import com.rangjin.chatapi.domain.channel.port.`in`.command.CreateChannelCommand

fun CreateChannelRequest.toCreateChannelCommand(userId: Long) =
    CreateChannelCommand(userId, name)

fun Channel.toChannelResponse() =
    ChannelDetailResponse(id, name, members.map { it.toUserWithoutPasswordResponse() }, createdAt, updatedAt)
