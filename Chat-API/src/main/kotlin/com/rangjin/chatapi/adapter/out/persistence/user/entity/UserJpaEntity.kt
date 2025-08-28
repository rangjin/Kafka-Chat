package com.rangjin.chatapi.adapter.out.persistence.user.entity

import com.rangjin.chatapi.adapter.out.persistence.channel.entity.ChannelJpaEntity
import com.rangjin.chatapi.adapter.out.persistence.common.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table

@Entity
@Table(name = "user")
class UserJpaEntity (

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

): BaseTimeEntity() {
}