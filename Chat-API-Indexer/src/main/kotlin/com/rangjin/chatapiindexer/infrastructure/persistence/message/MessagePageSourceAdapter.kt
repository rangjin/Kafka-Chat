package com.rangjin.chatapiindexer.infrastructure.persistence.message

import com.rangjin.chatapiindexer.application.port.out.PageSource
import com.rangjin.chatapiindexer.domain.Message
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class MessagePageSourceAdapter(

    private val messageJpaRepository: MessageJpaRepository

) : PageSource<Message> {

    override val name: String = "messages"

    override fun fetchPage(id: Long, pageable: Pageable) =
        messageJpaRepository.findByIdGreaterThanOrderByIdAsc(id, pageable)
            .map { it.toDomain() }

    override fun entityId(domain: Message): Long = domain.id

}