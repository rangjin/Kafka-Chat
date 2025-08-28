package com.rangjin.chatapi.domain.channel.port.out.persistence

import com.rangjin.chatapi.domain.channel.model.Channel

interface ChannelRepository {

    fun save(channel: Channel): Channel

}