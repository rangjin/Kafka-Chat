package com.rangjin.chatapi.domain.user.port.`in`.command

data class SignInCommand (

    val email: String,

    val rawPassword: String

) {
}