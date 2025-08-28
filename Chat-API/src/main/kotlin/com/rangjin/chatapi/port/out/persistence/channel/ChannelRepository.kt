package com.rangjin.chatapi.port.out.persistence.channel

import com.rangjin.chatapi.domain.channel.model.Channel

interface ChannelRepository {

    fun save(channel: Channel): Channel

}