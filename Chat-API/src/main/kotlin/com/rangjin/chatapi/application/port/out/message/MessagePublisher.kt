package com.rangjin.chatapi.application.port.out.message

import com.rangjin.chatapi.domain.channel.ChannelActivity
import com.rangjin.chatapi.domain.message.Message


interface MessagePublisher {

    fun publish(message: Message): Message

    fun publish(activity: ChannelActivity): ChannelActivity

    fun publishAll(activities: List<ChannelActivity>): List<ChannelActivity>

}