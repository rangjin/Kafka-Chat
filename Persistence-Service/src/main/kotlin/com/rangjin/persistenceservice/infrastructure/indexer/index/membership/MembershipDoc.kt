package com.rangjin.persistenceservice.infrastructure.indexer.index.membership

import com.rangjin.persistenceservice.infrastructure.persistence.membership.entity.MembershipRole
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.DateFormat
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import java.time.LocalDateTime

@Document(indexName = "memberships")
data class MembershipDoc (

    @Id
    val id: Long,

    @Field(type = FieldType.Long)
    val userId: Long,

    @Field(type = FieldType.Long)
    val channelId: Long,

    @Field(type = FieldType.Keyword)
    val role: MembershipRole,

    @Field(
        type = FieldType.Date,
        format = [DateFormat.strict_date_hour_minute_second_millis]
    )
    val joinedAt: LocalDateTime,

    @Field(
        type = FieldType.Date,
        format = [DateFormat.strict_date_hour_minute_second_millis]
    )
    val createdAt: LocalDateTime,

    @Field(
        type = FieldType.Date,
        format = [DateFormat.strict_date_hour_minute_second_millis]
    )
    val updatedAt: LocalDateTime

)