package com.rangjin.chatapi.infrastructure.persistence.membership.mapper

import com.rangjin.chatapi.domain.membership.Membership
import com.rangjin.chatapi.infrastructure.persistence.channel.mapper.ChannelMapper
import com.rangjin.chatapi.infrastructure.persistence.membership.entity.MembershipJpaEntity
import com.rangjin.chatapi.infrastructure.persistence.user.mapper.UserMapper

object MembershipMapper {

    fun toJpa(domain: Membership): MembershipJpaEntity =
        MembershipJpaEntity(
            id = domain.id,
            channel = ChannelMapper.toJpa(domain.channel),
            user = UserMapper.toJpa(domain.user),
            role = domain.role,
            joinedAt = domain.joinedAt
        )


    fun toDomain(entity: MembershipJpaEntity): Membership =
        Membership(
            id = entity.id,
            channel = ChannelMapper.toDomain(entity.channel),
            user = UserMapper.toDomain(entity.user),
            role = entity.role,
            joinedAt = entity.joinedAt,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )

}