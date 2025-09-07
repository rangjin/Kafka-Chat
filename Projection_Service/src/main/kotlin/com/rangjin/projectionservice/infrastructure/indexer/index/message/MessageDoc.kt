package com.rangjin.projectionservice.infrastructure.indexer.index.message

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.DateFormat
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import java.time.LocalDateTime

@Document(indexName = "messages")
data class MessageDoc(

    @Id
    val messageId: String,

    @Field(type = FieldType.Long)
    val seq: Long?,

    @Field(type = FieldType.Keyword)
    val channelId: Long,

    @Field(type = FieldType.Keyword)
    val senderId: Long,

    @Field(type = FieldType.Text)
    val content: String,

    @Field(
        type = FieldType.Date,
        format = [DateFormat.strict_date_hour_minute_second_millis]
    )
    val sentAt: LocalDateTime,

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