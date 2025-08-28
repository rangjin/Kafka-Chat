package com.rangjin.chatapi.adapter.out.persistence.channel.entity

import com.rangjin.chatapi.adapter.out.persistence.common.BaseTimeEntity
import com.rangjin.chatapi.adapter.out.persistence.user.entity.UserJpaEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table

@Entity
@Table(name = "channel")
class ChannelJpaEntity (

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

): BaseTimeEntity() {
}