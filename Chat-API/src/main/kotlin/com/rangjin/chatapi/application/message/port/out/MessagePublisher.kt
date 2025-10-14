package com.rangjin.chatapi.application.message.port.out

import com.rangjin.chatapi.application.event.DomainEvent


interface MessagePublisher {

    fun <T> publish(event: DomainEvent<T>)

    fun <T> publishAll(events: List<DomainEvent<T>>)

}