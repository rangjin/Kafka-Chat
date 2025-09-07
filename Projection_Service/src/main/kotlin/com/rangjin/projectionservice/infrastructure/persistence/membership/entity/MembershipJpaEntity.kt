package com.rangjin.projectionservice.infrastructure.persistence.membership.entity

import com.rangjin.projectionservice.infrastructure.persistence.channel.entity.ChannelJpaEntity
import com.rangjin.projectionservice.infrastructure.persistence.common.BaseTimeEntity
import com.rangjin.projectionservice.infrastructure.persistence.user.entity.UserJpaEntity
import jakarta.persistence.*
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