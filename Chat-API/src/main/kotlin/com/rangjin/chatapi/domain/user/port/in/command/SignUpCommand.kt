package com.rangjin.chatapi.domain.user.port.`in`.command

data class SignUpCommand (

    val username: String,

    val email: String,

    val rawPassword: String

)
