package com.rangjin.chatapi.presentation.api.message.controller

import com.rangjin.chatapi.application.message.port.`in`.SearchMessageUseCase
import com.rangjin.chatapi.application.message.port.`in`.SendMessageUseCase
import com.rangjin.chatapi.presentation.api.message.dto.request.SendMessageRequest
import com.rangjin.chatapi.presentation.api.message.dto.response.SendMessageResponse
import com.rangjin.chatapi.application.user.dto.AuthPrincipal
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/channel/{channelId}/messages")
class MessageController(

    private val sendMessageUseCase: SendMessageUseCase,

    private val searchMessageUseCase: SearchMessageUseCase

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

    @GetMapping("afterSeq/{seq}")
    fun getMessageAfterSeq(
        @AuthenticationPrincipal principal: AuthPrincipal,
        @PathVariable channelId: Long,
        @PathVariable seq: Long
    ) =
        searchMessageUseCase.searchAfterSeq(principal.userId, channelId, seq)

    @GetMapping("")
    fun getMessageByKeyword(
        @AuthenticationPrincipal principal: AuthPrincipal,
        @PathVariable channelId: Long,
        @RequestParam keyword: String
    ) =
        searchMessageUseCase.searchByChannelIdAndContent(principal.userId, channelId, keyword)

}