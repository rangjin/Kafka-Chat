package com.rangjin.chatapi.domain.channel

import java.time.LocalDateTime

data class Channel(

    val id: Long? = null,

    val name: String,

    var lastSeq: Long = 0,

//    val members: MutableSet<Membership> = mutableSetOf(),

    val createdAt: LocalDateTime? = null,

    val updatedAt: LocalDateTime? = null

) {

//    fun addOwner(user: User, joinedAt: LocalDateTime = LocalDateTime.now()) {
//        members += Membership(
//            id = null,
//            channelId = this.id, // 아직 null일 수 있음(신규)
//            user = user,
//            role = MembershipRole.OWNER,
//            joinedAt = joinedAt
//        )
//    }

    fun addLastSeq() = lastSeq++

}