package com.rangjin.chatapi.infrastructure.persistence.outbox.entity

enum class AggregateType(

    val dbValue: String

) {

    MESSAGE("message"),
    CHANNEL_ACTIVITY("channel-activity")
    ;

    companion object {
        fun fromDb(db: String): AggregateType =
            entries.firstOrNull { it.dbValue == db }
                ?: throw IllegalArgumentException("Unknown aggregate_type: $db")
    }

}