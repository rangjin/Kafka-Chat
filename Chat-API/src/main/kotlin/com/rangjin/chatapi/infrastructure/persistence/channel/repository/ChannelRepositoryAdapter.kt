package com.rangjin.chatapi.infrastructure.persistence.channel.repository

import com.rangjin.chatapi.domain.channel.Channel
import com.rangjin.chatapi.application.port.out.channel.ChannelRepository
import com.rangjin.chatapi.infrastructure.persistence.channel.mapper.ChannelMapper
import com.rangjin.chatapi.infrastructure.persistence.user.entity.UserJpaEntity
import jakarta.persistence.EntityManager
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ChannelRepositoryAdapter(

    private val channelJpaRepository: ChannelJpaRepository,

    private val em: EntityManager

) : ChannelRepository {

    override fun save(channel: Channel): Channel {
        val userRef: (Long) -> UserJpaEntity = {
            em.getReference(UserJpaEntity::class.java, it)
        }
        val entity = ChannelMapper.toJpa(channel, userRef)

        return ChannelMapper.toDomain(channelJpaRepository.save(entity))
    }

    override fun findById(channelId: Long): Channel? {
        val entity = channelJpaRepository.findByIdOrNull(channelId) ?: return null
        return ChannelMapper.toDomain(entity)
    }

    override fun findByUserId(userId: Long): List<Channel> =
        channelJpaRepository.findAllByUserId(userId)
            .map { ChannelMapper.toDomain(it) }

}