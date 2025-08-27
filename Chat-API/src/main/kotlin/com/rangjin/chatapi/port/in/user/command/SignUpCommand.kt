package com.rangjin.chatapi.port.`in`.user.command

data class SignUpCommand (

    val username: String,

    val email: String,

    val rawPassword: String

)
