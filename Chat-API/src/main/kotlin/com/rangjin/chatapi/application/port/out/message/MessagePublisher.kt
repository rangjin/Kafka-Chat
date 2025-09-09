package com.rangjin.chatapi.application.port.out.message

import com.rangjin.chatapi.domain.channel.ChannelEvent


interface MessagePublisher {

    fun <T> publish(event: ChannelEvent<T>)

    fun <T> publishAll(events: List<ChannelEvent<T>>)

}