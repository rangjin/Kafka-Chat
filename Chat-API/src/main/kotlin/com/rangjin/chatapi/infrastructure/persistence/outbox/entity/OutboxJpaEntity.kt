package com.rangjin.chatapi.infrastructure.persistence.outbox.entity

import com.fasterxml.jackson.annotation.JsonRawValue
import com.rangjin.chatapi.infrastructure.persistence.common.BaseTimeEntity
import com.rangjin.chatapi.infrastructure.persistence.outbox.converter.AggregateTypeConverter
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "outbox")
class OutboxJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Convert(converter = AggregateTypeConverter::class)
    @Column(name = "aggregate_type", nullable = false, length = 100)
    val aggregateType: AggregateType,

    @Column(name = "aggregate_id", nullable = false, length = 64)
    val aggregateId: Long,

    @Lob
    @Column(name = "payload", nullable = false, columnDefinition = "JSON")
    @JsonRawValue
    val payload: String,

    val timestamp: LocalDateTime

) : BaseTimeEntity()