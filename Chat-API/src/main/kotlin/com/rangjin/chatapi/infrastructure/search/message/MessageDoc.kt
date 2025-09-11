package com.rangjin.chatapi.infrastructure.search.message

import com.rangjin.chatapi.domain.message.Message
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.DateFormat
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import java.time.LocalDateTime
import java.util.UUID

@Document(indexName = "messages")
data class MessageDoc(

    @Id
    val id: Long,

    @Field(type = FieldType.Long)
    val seq: Long,

    @Field(type = FieldType.Keyword)
    val messageId: String,

    @Field(type = FieldType.Long)
    val channelId: Long,

    @Field(type = FieldType.Long)
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

) {

    fun toDomain(): Message =
        Message(
            id = id,
            seq = seq,
            messageId = UUID.fromString(messageId),
            channelId = channelId,
            senderId = senderId,
            content = content,
            sentAt = sentAt,
            createdAt = createdAt,
            updatedAt = updatedAt
        )

}