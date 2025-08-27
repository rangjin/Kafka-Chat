package com.rangjin.chatapi.port.`in`.user.command

data class SignInCommand (

    val email: String,

    val rawPassword: String

) {
}