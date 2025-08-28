package com.rangjin.chatapi.adapter.out.persistence.channel.mapper

import com.rangjin.chatapi.adapter.out.persistence.channel.entity.ChannelJpaEntity
import com.rangjin.chatapi.adapter.out.persistence.user.mapper.toDomain
import com.rangjin.chatapi.adapter.out.persistence.user.mapper.toEntity
import com.rangjin.chatapi.domain.channel.model.Channel

fun ChannelJpaEntity.toDomain() = Channel(
    id = id,
    name = name,
    members = members.map { it.toDomain() },
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun Channel.toEntity(): ChannelJpaEntity = ChannelJpaEntity(
    name = name,
    members = members.map { it.toEntity() }
)