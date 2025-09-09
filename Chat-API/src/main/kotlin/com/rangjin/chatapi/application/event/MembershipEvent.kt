package com.rangjin.chatapi.application.event

import com.rangjin.chatapi.domain.membership.Membership
import com.rangjin.chatapi.domain.membership.MembershipRole
import java.time.LocalDateTime

data class MembershipEvent(

    val id: Long,

    val channelId: Long,

    val userId: Long,

    val role: MembershipRole,

    val joinedAt: LocalDateTime,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime

) {

    companion object {

        fun fromDomain(domain: Membership): MembershipEvent =
            MembershipEvent(
                id = domain.id!!,
                channelId = domain.channel.id!!,
                userId = domain.user.id!!,
                role = domain.role,
                joinedAt = domain.joinedAt,
                createdAt = domain.createdAt!!,
                updatedAt = domain.updatedAt!!
            )

    }

}
