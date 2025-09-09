package com.rangjin.chatapi.infrastructure.persistence.channel.mapper

import com.rangjin.chatapi.domain.channel.Channel
import com.rangjin.chatapi.infrastructure.persistence.channel.entity.ChannelJpaEntity

object ChannelMapper {

    fun toJpa(domain: Channel): ChannelJpaEntity {
        val channelJpa = ChannelJpaEntity(
            id = domain.id,
            lastSeq = domain.lastSeq,
            name = domain.name,
        )

        return channelJpa
    }

    fun toDomain(jpa: ChannelJpaEntity): Channel =
        Channel(
            id = jpa.id,
            lastSeq = jpa.lastSeq,
            name = jpa.name,
            createdAt = jpa.createdAt,
            updatedAt = jpa.updatedAt
        )

}