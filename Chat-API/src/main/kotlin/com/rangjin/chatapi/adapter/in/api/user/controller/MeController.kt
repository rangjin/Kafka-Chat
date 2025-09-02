package com.rangjin.chatapi.adapter.`in`.api.user.controller

import com.rangjin.chatapi.adapter.`in`.api.channel.dto.response.ChannelDetailResponse
import com.rangjin.chatapi.adapter.`in`.api.channel.mapper.toChannelResponse
import com.rangjin.chatapi.adapter.`in`.auth.AuthPrincipal
import com.rangjin.chatapi.domain.channel.port.`in`.usecase.ReadMyChannelsUseCase
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users/me")
class MeController(

    private val readMyChannelsUseCase: ReadMyChannelsUseCase

) {

    @GetMapping("/channels")
    fun getMyChannels(
        @AuthenticationPrincipal principal: AuthPrincipal
    ): List<ChannelDetailResponse> =
        readMyChannelsUseCase.getMyChannels(principal.userId)
            .map { it.toChannelResponse() }

}