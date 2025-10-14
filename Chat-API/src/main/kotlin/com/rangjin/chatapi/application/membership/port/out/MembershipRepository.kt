package com.rangjin.chatapi.application.membership.port.out

import com.rangjin.chatapi.domain.membership.Membership

interface MembershipRepository {

    fun save(membership: Membership): Membership

    fun saveAll(memberships: List<Membership>): List<Membership>

    fun existsByUserIdAndChannelId(userId: Long, channelId: Long): Boolean

    fun findByUserIdAndChannelId(userId: Long, channelId: Long): Membership?

    fun findAllByUserId(userId: Long): List<Membership>

    fun findAllByChannelId(channelId: Long): List<Membership>

    fun delete(membership: Membership)

}