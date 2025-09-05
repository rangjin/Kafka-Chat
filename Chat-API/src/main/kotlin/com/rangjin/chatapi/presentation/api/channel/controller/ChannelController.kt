package com.rangjin.chatapi.presentation.api.channel.controller

import com.rangjin.chatapi.presentation.auth.AuthPrincipal
import com.rangjin.chatapi.application.port.`in`.channel.CreateChannelUseCase
import com.rangjin.chatapi.application.port.`in`.channel.InvitationUseCase
import com.rangjin.chatapi.application.port.`in`.channel.WithdrawUseCase
import com.rangjin.chatapi.presentation.api.channel.dto.request.CreateChannelRequest
import com.rangjin.chatapi.presentation.api.channel.dto.request.InvitationRequest
import com.rangjin.chatapi.presentation.api.channel.dto.response.ChannelDetailResponse
import com.rangjin.chatapi.presentation.api.channel.dto.response.InvitationResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/channel")
class ChannelController(

    private val createChannelUseCase: CreateChannelUseCase,

    private val invitationUseCase: InvitationUseCase,

    private val withdrawUseCase: WithdrawUseCase

) {

    @PostMapping("")
    fun postCreate(
        @AuthenticationPrincipal principal: AuthPrincipal,
        @RequestBody req: CreateChannelRequest
    ): ChannelDetailResponse {
        val channel = createChannelUseCase.createChannel(principal.userId, req.name)

        return ChannelDetailResponse.from(channel)
    }

    @PostMapping("/{channelId}/invitation")
    fun postInvitation(
        @AuthenticationPrincipal principal: AuthPrincipal,
        @PathVariable channelId: Long,
        @RequestBody req: InvitationRequest
    ): InvitationResponse =
        InvitationResponse(
            invitedUserIds = invitationUseCase.invitationToChannel(principal.userId, channelId, req.emails)
        )

    @PostMapping("/{channelId}/withdraw")
    fun postWithdraw(
        @AuthenticationPrincipal principal: AuthPrincipal,
        @PathVariable channelId: Long
    ) =
        withdrawUseCase.withdrawFromChannel(principal.userId, channelId)

}