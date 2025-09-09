package com.rangjin.chatapi.infrastructure.persistence.membership.mapper

import com.rangjin.chatapi.domain.membership.Membership
import com.rangjin.chatapi.infrastructure.persistence.channel.entity.ChannelJpaEntity
import com.rangjin.chatapi.infrastructure.persistence.membership.entity.MembershipJpaEntity
import com.rangjin.chatapi.infrastructure.persistence.user.mapper.UserMapper

object MembershipMapper {

    fun toJpa(
        domain: Membership,
        ownerRequiredChannel: (Long) -> ChannelJpaEntity
    ): MembershipJpaEntity =
        MembershipJpaEntity(
            id = domain.id,
            channel = ownerRequiredChannel(domain.channelId!!),
            user = UserMapper.toJpa(domain.user),
            role = domain.role,
            joinedAt = domain.joinedAt,
        )


    fun toDomain(entity: MembershipJpaEntity): Membership =
        Membership(
            id = entity.id,
            channelId = entity.channel.id,
            user = UserMapper.toDomain(entity.user),
            role = entity.role,
            joinedAt = entity.joinedAt,
        )

}