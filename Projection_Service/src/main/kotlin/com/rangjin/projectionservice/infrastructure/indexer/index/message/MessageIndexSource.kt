package com.rangjin.projectionservice.infrastructure.indexer.index.message

import com.rangjin.projectionservice.infrastructure.indexer.config.IndexSource
import com.rangjin.projectionservice.infrastructure.persistence.message.entity.MessageJpaEntity
import com.rangjin.projectionservice.infrastructure.persistence.message.repository.MessageJpaRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class MessageIndexSource(

    private val messageJpaRepository: MessageJpaRepository

) : IndexSource<MessageJpaEntity, MessageDoc> {

    override val name: String = "messages"

    override val docClass: KClass<MessageDoc> = MessageDoc::class

    override fun fetchPage(
        afterId: Long,
        pageable: Pageable
    ): List<MessageJpaEntity> =
        messageJpaRepository.findByIdGreaterThanOrderByIdAsc(afterId, pageable)

    override fun entityId(entity: MessageJpaEntity): Long =
        entity.id!!

    override fun toDoc(entity: MessageJpaEntity): MessageDoc =
        MessageDoc(
            entity.messageId,
            entity.seq,
            entity.channel.id!!,
            entity.sender.id!!,
            entity.content,
            entity.sentAt,
            entity.createdAt,
            entity.updatedAt
        )

}