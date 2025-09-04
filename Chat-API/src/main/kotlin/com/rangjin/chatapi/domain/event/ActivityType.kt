package com.rangjin.chatapi.domain.event

enum class ActivityType(

    val value: String

) {

    JOIN("Join"),
    LEAVE("Leave"),
    ;

}