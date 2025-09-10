package com.rangjin.chatapiindexer.infrastructure.search.model.message

import com.rangjin.chatapiindexer.application.port.out.DocMapper
import com.rangjin.chatapiindexer.domain.Message
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class MessageDocMapper : DocMapper<Message, MessageDoc> {

    override val domain: String = "messages"

    override val docClass: KClass<MessageDoc> = MessageDoc::class

    override fun toDoc(domain: Message): MessageDoc =
        MessageDoc.fromDomain(domain)

}