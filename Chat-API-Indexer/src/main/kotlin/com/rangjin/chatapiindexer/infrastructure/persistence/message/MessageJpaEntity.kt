package com.rangjin.chatapiindexer.infrastructure.persistence.message

import com.rangjin.chatapiindexer.domain.Message
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

    val sentAt: LocalDateTime,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime,

    ) {

    fun toDomain(): Message =
        Message(
            id!!,
            seq,
            messageId,
            channelId,
            senderId,
            content,
            sentAt,
            createdAt,
            updatedAt
        )

}