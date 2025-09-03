package com.rangjin.chatapi.infrastructure.persistence.outbox.entity

import com.rangjin.chatapi.infrastructure.persistence.common.BaseTimeEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "message_outbox",
    indexes = [
        Index(name = "idx_outbox_status_next", columnList = "status,next_retry_at"),
        Index(name = "uk_outbox_message_id", columnList = "message_id", unique = true)
    ]
)
class MessageOutboxJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val messageId: String,

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    val payload: String,

    @Enumerated(EnumType.STRING)
    var status: MessageOutboxStatus = MessageOutboxStatus.SCHEDULED,

    var attempts: Int = 0,

    var nextRetryAt: LocalDateTime? = null,

    val sendAt: LocalDateTime

) : BaseTimeEntity()