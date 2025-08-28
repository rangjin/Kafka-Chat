package com.rangjin.chatapi.adapter.out.persistence.channel.entity

import com.rangjin.chatapi.adapter.out.persistence.common.BaseTimeEntity
import com.rangjin.chatapi.adapter.out.persistence.user.entity.UserJpaEntity
import jakarta.persistence.*

@Entity
@Table(name = "channel")
class ChannelJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val name: String,

    @ManyToMany
    @JoinTable(
        name = "channel_members",
        joinColumns = [JoinColumn(name = "channel_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    val members: List<UserJpaEntity> = emptyList()

) : BaseTimeEntity()