package com.rangjin.chatapi.adapter.out.persistence.channel.repository

import com.rangjin.chatapi.adapter.out.persistence.channel.mapper.toDomain
import com.rangjin.chatapi.adapter.out.persistence.channel.mapper.toEntity
import com.rangjin.chatapi.domain.channel.model.Channel
import com.rangjin.chatapi.port.out.persistence.channel.ChannelRepository
import org.springframework.stereotype.Component

@Component
class ChannelRepositoryAdapter(

    private val channelJpaRepository: ChannelJpaRepository

) : ChannelRepository {

    override fun save(channel: Channel): Channel {
        val entity = channel.toEntity()
        val saved = channelJpaRepository.save(entity)
        return saved.toDomain()
    }

}