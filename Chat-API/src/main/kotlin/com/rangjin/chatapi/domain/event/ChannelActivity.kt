package com.rangjin.chatapi.domain.event

import com.rangjin.chatapi.domain.membership.MembershipRole
import java.time.LocalDateTime

sealed interface ChannelActivity {

    val channelId: Long

    val userId: Long

    val type: ActivityType

    val occurredAt: LocalDateTime

    data class Joined(
        override val channelId: Long,
        override val userId: Long,
        val role: MembershipRole,
        override val type: ActivityType = ActivityType.JOIN,
        override val occurredAt: LocalDateTime
    ) : ChannelActivity

    data class Left(
        override val channelId: Long,
        override val userId: Long,
        override val type: ActivityType = ActivityType.LEAVE,
        override val occurredAt: LocalDateTime
    ) : ChannelActivity

}