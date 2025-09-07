package com.rangjin.projectionservice.infrastructure.persistence.channel.entity

import com.rangjin.projectionservice.infrastructure.persistence.common.BaseTimeEntity
import com.rangjin.projectionservice.infrastructure.persistence.membership.entity.MembershipJpaEntity
import jakarta.persistence.*

@Entity
@Table(name = "channel")
class ChannelJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val name: String,

    var lastSeq: Long = 0,

    @OneToMany(mappedBy = "channel", cascade = [CascadeType.ALL], orphanRemoval = true)
    val memberships: MutableSet<MembershipJpaEntity> = mutableSetOf()

) : BaseTimeEntity()