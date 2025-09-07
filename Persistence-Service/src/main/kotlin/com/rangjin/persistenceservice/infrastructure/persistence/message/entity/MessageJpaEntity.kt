package com.rangjin.persistenceservice.infrastructure.persistence.message.entity

import com.rangjin.persistenceservice.infrastructure.persistence.channel.entity.ChannelJpaEntity
import com.rangjin.persistenceservice.infrastructure.persistence.common.BaseTimeEntity
import com.rangjin.persistenceservice.infrastructure.persistence.user.entity.UserJpaEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "message")
class MessageJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val seq: Long,

    val messageId: String,

    @ManyToOne(fetch = FetchType.LAZY)
    val channel: ChannelJpaEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    val sender: UserJpaEntity,

    val content: String,

    val sentAt: LocalDateTime

) : BaseTimeEntity()