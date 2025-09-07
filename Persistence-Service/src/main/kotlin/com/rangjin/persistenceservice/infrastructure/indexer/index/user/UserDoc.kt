package com.rangjin.persistenceservice.infrastructure.indexer.index.user

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.DateFormat
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import java.time.LocalDateTime

@Document(indexName = "users")
data class UserDoc(

    @Id
    val id: Long,

    @Field(type = FieldType.Keyword)
    val username: String,

    @Field(type = FieldType.Keyword)
    val email: String,

    @Field(type = FieldType.Keyword)
    val password: String,

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