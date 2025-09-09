package com.rangjin.chatapi.presentation.api.message.controller

import com.rangjin.chatapi.application.port.`in`.message.SendMessageUseCase
import com.rangjin.chatapi.presentation.api.message.dto.request.SendMessageRequest
import com.rangjin.chatapi.presentation.api.message.dto.response.SendMessageResponse
import com.rangjin.chatapi.presentation.auth.AuthPrincipal
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/channel/{channelId}/messages")
class MessageController(

    private val sendMessageUseCase: SendMessageUseCase

) {

    @PostMapping("")
    fun postSendMessage(
        @AuthenticationPrincipal principal: AuthPrincipal,
        @PathVariable channelId: Long,
        @RequestBody req: SendMessageRequest
    ): SendMessageResponse =
        SendMessageResponse.from(
            sendMessageUseCase.sendMessage(
                channelId, principal.userId, req.message
            )
        )

}