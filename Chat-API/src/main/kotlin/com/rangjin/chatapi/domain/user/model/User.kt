package com.rangjin.chatapi.domain.user.model

data class User (

    val id: Long? = null,

    val username: String,

    val email: String,

    val passwordHash: String,

)