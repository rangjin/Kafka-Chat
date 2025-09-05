package com.rangjin.chatapi.application.port.`in`.channel

interface WithdrawUseCase {

    fun withdrawFromChannel(userId: Long, channelId: Long): Boolean

}