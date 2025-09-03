package com.rangjin.chatapi.infrastructure.persistence.channel.entity

import com.rangjin.chatapi.infrastructure.persistence.common.BaseTimeEntity
import com.rangjin.chatapi.infrastructure.persistence.membership.entity.MembershipJpaEntity
import jakarta.persistence.*

@Entity
@Table(name = "channel")
class ChannelJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val name: String,

    @OneToMany(mappedBy = "channel", cascade = [CascadeType.ALL], orphanRemoval = true)
    val memberships: MutableSet<MembershipJpaEntity> = mutableSetOf()

) : BaseTimeEntity()