package com.rangjin.chatapi.application.membership.port.`in`

interface WithdrawUseCase {

    fun withdrawFromChannel(userId: Long, channelId: Long): Boolean

}