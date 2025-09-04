package com.rangjin.chatapi.infrastructure.persistence.membership.entity

import com.rangjin.chatapi.domain.membership.MembershipRole
import com.rangjin.chatapi.infrastructure.persistence.channel.entity.ChannelJpaEntity
import com.rangjin.chatapi.infrastructure.persistence.common.BaseTimeEntity
import com.rangjin.chatapi.infrastructure.persistence.user.entity.UserJpaEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.time.LocalDateTime


@Entity
@Table(
    name = "membership",
    uniqueConstraints = [
        UniqueConstraint(name = "uk_channel_user", columnNames = ["channel_id", "user_id"])
    ],
    indexes = [
        Index(name = "idx_membership_channel", columnList = "channel_id"),
        Index(name = "idx_membership_user", columnList = "user_id")
    ]
)
class MembershipJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", nullable = false)
    var channel: ChannelJpaEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserJpaEntity,

    @Enumerated(EnumType.STRING)
    var role: MembershipRole = MembershipRole.MEMBER,

    @Column(name = "joined_at", nullable = false)
    var joinedAt: LocalDateTime = LocalDateTime.now()

) : BaseTimeEntity()