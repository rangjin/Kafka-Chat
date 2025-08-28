package com.rangjin.chatapi.adapter.out.persistence.user.entity

import com.rangjin.chatapi.adapter.out.persistence.channel.entity.ChannelJpaEntity
import com.rangjin.chatapi.adapter.out.persistence.common.BaseTimeEntity
import jakarta.persistence.*

@Entity
@Table(name = "user")
class UserJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(unique = true, nullable = false)
    val username: String,

    @Column(unique = true, nullable = false)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @ManyToMany(
        mappedBy = "members",
        fetch = FetchType.LAZY
    )
    val channels: List<ChannelJpaEntity> = ArrayList()

) : BaseTimeEntity()