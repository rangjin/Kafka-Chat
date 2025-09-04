package com.rangjin.chatapi.infrastructure.persistence.channel.mapper

import com.rangjin.chatapi.domain.channel.Channel
import com.rangjin.chatapi.domain.membership.Membership
import com.rangjin.chatapi.infrastructure.persistence.channel.entity.ChannelJpaEntity
import com.rangjin.chatapi.infrastructure.persistence.membership.entity.MembershipJpaEntity
import com.rangjin.chatapi.infrastructure.persistence.membership.entity.MembershipRole
import com.rangjin.chatapi.infrastructure.persistence.user.entity.UserJpaEntity
import com.rangjin.chatapi.infrastructure.persistence.user.mapper.UserMapper

object ChannelMapper {

    fun toJpa(domain: Channel, ownerRequiredUser: (Long) -> UserJpaEntity): ChannelJpaEntity {
        val channelJpa = ChannelJpaEntity(
            id = domain.id,
            name = domain.name,
            memberships = mutableSetOf()
        )

        domain.members.forEach { m ->
            val memJpa = MembershipJpaEntity(
                id = m.id,
                channel = channelJpa,
                user = ownerRequiredUser(m.user.id!!),
                role = MembershipRole.valueOf(m.role.name),
                joinedAt = m.joinedAt
            )
            channelJpa.memberships.add(memJpa)
        }
        return channelJpa
    }

    fun toDomain(jpa: ChannelJpaEntity): Channel =
        Channel(
            id = jpa.id,
            name = jpa.name,
            members = jpa.memberships.map { m ->
                Membership(
                    id = m.id,
                    channelId = jpa.id,
                    user = UserMapper.toDomain(m.user),
                    role = m.role,
                    joinedAt = m.joinedAt,
                    createdAt = m.createdAt,
                    updatedAt = m.updatedAt
                )
            }.toMutableSet(),
            createdAt = jpa.createdAt,
            updatedAt = jpa.updatedAt
        )

}