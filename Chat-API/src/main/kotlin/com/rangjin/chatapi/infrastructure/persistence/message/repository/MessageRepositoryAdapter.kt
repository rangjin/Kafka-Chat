package com.rangjin.chatapi.infrastructure.persistence.message.repository

import com.rangjin.chatapi.application.port.out.message.MessageRepository
import com.rangjin.chatapi.domain.message.Message
import com.rangjin.chatapi.infrastructure.persistence.channel.entity.ChannelJpaEntity
import com.rangjin.chatapi.infrastructure.persistence.message.mapper.MessageMapper
import com.rangjin.chatapi.infrastructure.persistence.user.entity.UserJpaEntity
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Component

@Component
class MessageRepositoryAdapter(

    private val messageJpaRepository: MessageJpaRepository,

    private val em: EntityManager

) : MessageRepository {

    private val userRef: (Long) -> UserJpaEntity = {
        em.getReference(UserJpaEntity::class.java, it)
    }

    private val channelRef: (Long) -> ChannelJpaEntity = {
        em.getReference(ChannelJpaEntity::class.java, it)
    }

    override fun save(message: Message): Message =
        MessageMapper.toDomain(
            messageJpaRepository.save(
                MessageMapper.toJpa(
                    message,
                    userRef,
                    channelRef
                )
            )
        )

}