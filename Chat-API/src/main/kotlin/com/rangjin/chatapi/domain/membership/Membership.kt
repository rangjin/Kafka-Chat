package com.rangjin.chatapi.domain.membership

import com.rangjin.chatapi.domain.user.User
import com.rangjin.chatapi.infrastructure.persistence.membership.entity.MembershipRole
import java.time.LocalDateTime

data class Membership(

    val id: Long? = null,

    val channelId: Long? = null,

    val user: User,

    val role: MembershipRole,

    val joinedAt: LocalDateTime,

    val createdAt: LocalDateTime? = null,

    val updatedAt: LocalDateTime? = null,

)
