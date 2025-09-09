package com.rangjin.chatapi.application.port.out.channel

import com.rangjin.chatapi.domain.channel.Channel

interface ChannelRepository {

    fun save(channel: Channel): Channel

    fun existsById(channelId: Long): Boolean

    fun findById(channelId: Long): Channel?

}