package com.rangjin.chatapi.infrastructure.persistence.membership.repository

import com.rangjin.chatapi.infrastructure.persistence.channel.entity.ChannelJpaEntity
import com.rangjin.chatapi.infrastructure.persistence.membership.entity.MembershipJpaEntity
import com.rangjin.chatapi.infrastructure.persistence.user.entity.UserJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MembershipJpaRepository : JpaRepository<MembershipJpaEntity, Long> {

    fun existsByUserAndChannel(user: UserJpaEntity, channel: ChannelJpaEntity): Boolean

    fun findByUserAndChannel(user: UserJpaEntity, channel: ChannelJpaEntity): MembershipJpaEntity?

    fun findAllByUser(user: UserJpaEntity): List<MembershipJpaEntity>

    fun findAllByChannel(channel: ChannelJpaEntity): List<MembershipJpaEntity>

}