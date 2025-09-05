package com.rangjin.chatapi.application.port.out.message

import com.rangjin.chatapi.domain.event.ChannelActivity
import com.rangjin.chatapi.domain.event.MessageSent


interface MessagePublisher {

    fun publish(message: MessageSent): MessageSent

    fun publish(activity: ChannelActivity): ChannelActivity

    fun publishAll(activities: List<ChannelActivity>): List<ChannelActivity>

}