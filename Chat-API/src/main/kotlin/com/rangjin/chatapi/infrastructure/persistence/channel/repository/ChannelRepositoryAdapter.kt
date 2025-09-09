package com.rangjin.chatapi.infrastructure.persistence.channel.repository

import com.rangjin.chatapi.application.port.out.channel.ChannelRepository
import com.rangjin.chatapi.domain.channel.Channel
import com.rangjin.chatapi.infrastructure.persistence.channel.mapper.ChannelMapper
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ChannelRepositoryAdapter(

    private val channelJpaRepository: ChannelJpaRepository,

    ) : ChannelRepository {

    override fun save(channel: Channel): Channel =
        ChannelMapper.toDomain(
            channelJpaRepository.save(
                ChannelMapper.toJpa(channel)
            )
        )

    override fun existsById(channelId: Long): Boolean =
        channelJpaRepository.existsById(channelId)

    override fun findById(channelId: Long): Channel? {
        val entity = channelJpaRepository.findByIdOrNull(channelId) ?: return null
        return ChannelMapper.toDomain(entity)
    }

}