package com.rangjin.persistenceservice.infrastructure.indexer.index.channel

import com.rangjin.persistenceservice.infrastructure.indexer.config.IndexSource
import com.rangjin.persistenceservice.infrastructure.persistence.channel.entity.ChannelJpaEntity
import com.rangjin.persistenceservice.infrastructure.persistence.channel.repository.ChannelJpaRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class ChannelIndexSource(

    private val channelJpaRepository: ChannelJpaRepository

) : IndexSource<ChannelJpaEntity, ChannelDoc> {

    override val name: String = "channels"

    override val docClass: KClass<ChannelDoc> = ChannelDoc::class

    override fun fetchPage(
        afterId: Long,
        pageable: Pageable
    ): List<ChannelJpaEntity> =
        channelJpaRepository.findByIdGreaterThanOrderByIdAsc(afterId, pageable)

    override fun entityId(entity: ChannelJpaEntity): Long =
        entity.id!!

    override fun toDoc(entity: ChannelJpaEntity): ChannelDoc =
        ChannelDoc(
            entity.id!!,
            entity.name,
            entity.createdAt,
            entity.updatedAt
        )
}