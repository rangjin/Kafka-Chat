package com.rangjin.chatapi.infrastructure.persistence.outbox.entity

enum class MessageOutboxStatus {

    SCHEDULED, SENT, FAILED, DEAD

}