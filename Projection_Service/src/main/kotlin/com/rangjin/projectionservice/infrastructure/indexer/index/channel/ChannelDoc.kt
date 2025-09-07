package com.rangjin.projectionservice.infrastructure.indexer.index.channel

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.DateFormat
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import java.time.LocalDateTime

@Document(indexName = "channels")
data class ChannelDoc(

    @Id
    val channelId: Long,

    @Field(type = FieldType.Keyword)
    val name: String,

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