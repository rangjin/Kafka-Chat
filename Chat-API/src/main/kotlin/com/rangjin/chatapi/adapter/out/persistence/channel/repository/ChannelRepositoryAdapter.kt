package com.rangjin.chatapi.adapter.out.persistence.channel.repository

import com.rangjin.chatapi.adapter.out.persistence.channel.mapper.toDomain
import com.rangjin.chatapi.adapter.out.persistence.channel.mapper.toEntity
import com.rangjin.chatapi.adapter.out.persistence.user.repository.UserJpaRepository
import com.rangjin.chatapi.domain.channel.model.Channel
import com.rangjin.chatapi.domain.channel.port.out.persistence.ChannelRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ChannelRepositoryAdapter(

    private val channelJpaRepository: ChannelJpaRepository,

    private val userJpaRepository: UserJpaRepository

) : ChannelRepository {

    override fun save(channel: Channel): Channel {
        val entity = channel.toEntity()
        val saved = channelJpaRepository.save(entity)
        return saved.toDomain()
    }

    override fun findByUserId(userId: Long): List<Channel> =
        userJpaRepository.findByIdOrNull(userId)!!
            .channels
            .map { it.toDomain() }

}