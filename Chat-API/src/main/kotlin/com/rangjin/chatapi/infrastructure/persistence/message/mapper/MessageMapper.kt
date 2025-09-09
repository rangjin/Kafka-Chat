package com.rangjin.chatapi.infrastructure.persistence.message.mapper

import com.rangjin.chatapi.domain.message.Message
import com.rangjin.chatapi.infrastructure.persistence.channel.entity.ChannelJpaEntity
import com.rangjin.chatapi.infrastructure.persistence.message.entity.MessageJpaEntity
import com.rangjin.chatapi.infrastructure.persistence.user.entity.UserJpaEntity
import java.util.*

object MessageMapper {

    fun toJpa(
        domain: Message,
        ownerRequiredUser: (Long) -> UserJpaEntity,
        ownerRequiredChannel: (Long) -> ChannelJpaEntity
    ): MessageJpaEntity =
        MessageJpaEntity(
            id = domain.id,
            seq = domain.seq,
            messageId = domain.messageId.toString(),
            content = domain.content,
            sender = ownerRequiredUser(domain.senderId),
            channel = ownerRequiredChannel(domain.channelId),
            sentAt = domain.sentAt
        )

    fun toDomain(entity: MessageJpaEntity): Message =
        Message(
            id = entity.id!!,
            seq = entity.seq,
            messageId = UUID.fromString(entity.messageId),
            content = entity.content,
            senderId = entity.sender.id!!,
            channelId = entity.channel.id!!,
            sentAt = entity.sentAt
        )

}