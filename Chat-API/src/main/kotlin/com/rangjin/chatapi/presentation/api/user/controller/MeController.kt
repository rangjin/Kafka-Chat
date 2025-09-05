package com.rangjin.chatapi.presentation.api.user.controller

import com.rangjin.chatapi.presentation.auth.AuthPrincipal
import com.rangjin.chatapi.application.port.`in`.channel.ReadMyChannelsUseCase
import com.rangjin.chatapi.presentation.api.channel.dto.response.MyChannelIdsResponse
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
    ): MyChannelIdsResponse =
        MyChannelIdsResponse(
            principal.userId,
            readMyChannelsUseCase.getMyChannels(principal.userId)
                .map { it.id!! }
        )

}