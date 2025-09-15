package com.rangjin.chatapi.infrastructure.search.message

import co.elastic.clients.elasticsearch._types.SortOrder
import com.rangjin.chatapi.application.port.out.message.MessageSearch
import com.rangjin.chatapi.domain.message.Message
import org.springframework.data.elasticsearch.client.elc.NativeQuery
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.stereotype.Component

@Component
class MessageSearchAdapter(

    private val ops: ElasticsearchOperations

): MessageSearch {

    override fun searchByChannelIdAndContent(channelId: Long, keyword: String): List<Message> {
        val query = NativeQuery.builder()
            .withQuery { q ->
                q.bool { b ->
                    b.must { it.term { t -> t.field("channelId").value(channelId) } }
                    b.must { it.match { m -> m.field("content").query(keyword) } }
                }
            }
            .withSort { s -> s.field { f -> f.field("seq").order(SortOrder.Desc) } }
            .build()

        return ops.search(query, MessageDoc::class.java)
            .map { it.content.toDomain() }.toList()
    }

}