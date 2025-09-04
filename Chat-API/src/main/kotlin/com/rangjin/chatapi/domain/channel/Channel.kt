package com.rangjin.chatapi.domain.channel

import com.rangjin.chatapi.domain.membership.Membership
import com.rangjin.chatapi.domain.user.User
import com.rangjin.chatapi.infrastructure.persistence.membership.entity.MembershipRole
import java.time.LocalDateTime

data class Channel(

    val id: Long? = null,

    val name: String,

    val members: MutableSet<Membership> = mutableSetOf(),

    val createdAt: LocalDateTime? = null,

    val updatedAt: LocalDateTime? = null

) {

    fun addOwner(user: User, joinedAt: LocalDateTime = LocalDateTime.now()) {
        members += Membership(
            id = null,
            channelId = this.id, // 아직 null일 수 있음(신규)
            user = user,
            role = MembershipRole.OWNER,
            joinedAt = joinedAt
        )
    }

    fun existsMember(userId: Long): Boolean =
        members.any { it.user.id == userId }

    fun addMember(user: User, joinedAt: LocalDateTime = LocalDateTime.now()) {
        members += Membership(
            id = null,
            channelId = this.id,
            user = user,
            role = MembershipRole.MEMBER,
            joinedAt = joinedAt
        )
    }

}