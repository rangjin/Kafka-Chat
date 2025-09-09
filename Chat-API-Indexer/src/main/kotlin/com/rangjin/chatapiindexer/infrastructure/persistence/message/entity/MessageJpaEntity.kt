package com.rangjin.chatapiindexer.infrastructure.persistence.message.entity

import com.rangjin.chatapiindexer.infrastructure.persistence.common.BaseTimeEntity
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

    val channelId: Long,

    val senderId: Long,

    val content: String,

    val sentAt: LocalDateTime

) : BaseTimeEntity()