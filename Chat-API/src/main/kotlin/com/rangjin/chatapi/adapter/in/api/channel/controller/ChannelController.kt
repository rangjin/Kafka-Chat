package com.rangjin.chatapi.adapter.`in`.api.channel.controller

import com.rangjin.chatapi.adapter.`in`.api.channel.dto.request.CreateChannelRequest
import com.rangjin.chatapi.adapter.`in`.api.channel.dto.response.ChannelResponse
import com.rangjin.chatapi.adapter.`in`.api.channel.mapper.toChannelResponse
import com.rangjin.chatapi.adapter.`in`.api.channel.mapper.toCreateChannelCommand
import com.rangjin.chatapi.adapter.`in`.auth.AuthPrincipal
import com.rangjin.chatapi.port.`in`.channel.usecase.CreateChannelUseCase
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/channel")
class ChannelController (

    private val createChannelUseCase: CreateChannelUseCase

) {

    @PostMapping("")
    fun postCreate(
        @AuthenticationPrincipal principal: AuthPrincipal,
        @RequestBody createChannelRequest: CreateChannelRequest
    ): ChannelResponse {
        val channel = createChannelUseCase.createChannel(
            createChannelRequest.toCreateChannelCommand(principal.userId)
        )

        return channel.toChannelResponse()
    }
}