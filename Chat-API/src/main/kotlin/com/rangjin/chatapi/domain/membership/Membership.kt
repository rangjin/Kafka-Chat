package com.rangjin.chatapi.domain.membership

import com.rangjin.chatapi.domain.channel.Channel
import com.rangjin.chatapi.domain.user.User
import java.time.LocalDateTime

data class Membership(

    val id: Long? = null,

    val channel: Channel,

    val user: User,

    val role: MembershipRole,

    val joinedAt: LocalDateTime = LocalDateTime.now(),

    val createdAt: LocalDateTime? = null,

    val updatedAt: LocalDateTime? = null

)
