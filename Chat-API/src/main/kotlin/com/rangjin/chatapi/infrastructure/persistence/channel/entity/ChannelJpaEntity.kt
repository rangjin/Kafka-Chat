package com.rangjin.chatapi.infrastructure.persistence.channel.entity

import com.rangjin.chatapi.infrastructure.persistence.common.BaseTimeEntity
import jakarta.persistence.*

@Entity
@Table(name = "channel")
class ChannelJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val name: String,

    var lastSeq: Long = 0,

    ) : BaseTimeEntity()