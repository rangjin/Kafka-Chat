package com.rangjin.chatapi.adapter.`in`.api.message.controller

import com.rangjin.chatapi.adapter.`in`.api.message.dto.request.SendMessageRequest
import com.rangjin.chatapi.adapter.`in`.api.message.dto.response.SendMessageResponse
import com.rangjin.chatapi.adapter.`in`.api.message.mapper.toSendMessageCommand
import com.rangjin.chatapi.adapter.`in`.api.message.mapper.toSendMessageResponse
import com.rangjin.chatapi.adapter.`in`.auth.AuthPrincipal
import com.rangjin.chatapi.domain.message.port.`in`.usecase.SendMessageUseCase
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
        sendMessageUseCase.sendMessage(
            req.toSendMessageCommand(channelId, principal.userId)
        ).toSendMessageResponse()

}