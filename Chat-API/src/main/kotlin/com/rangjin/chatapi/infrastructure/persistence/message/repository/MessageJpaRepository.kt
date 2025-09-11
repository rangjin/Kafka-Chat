package com.rangjin.chatapi.infrastructure.persistence.message.repository

import com.rangjin.chatapi.infrastructure.persistence.channel.entity.ChannelJpaEntity
import com.rangjin.chatapi.infrastructure.persistence.message.entity.MessageJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MessageJpaRepository : JpaRepository<MessageJpaEntity, Long> {

    fun findAllByChannelAndSeqAfter(channel: ChannelJpaEntity, seq: Long): List<MessageJpaEntity>

}