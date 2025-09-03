package com.rangjin.chatapi.presentation.api.user.dto.request

data class SignUpRequest(

    val username: String,

    val email: String,

    val password: String

)