package com.rangjin.chatapi.adapter.`in`.api.user.mapper

import com.rangjin.chatapi.adapter.`in`.api.user.dto.request.SignInRequest
import com.rangjin.chatapi.adapter.`in`.api.user.dto.request.SignUpRequest
import com.rangjin.chatapi.adapter.`in`.api.user.dto.response.UserSummaryResponse
import com.rangjin.chatapi.domain.user.model.User
import com.rangjin.chatapi.domain.user.port.`in`.command.SignInCommand
import com.rangjin.chatapi.domain.user.port.`in`.command.SignUpCommand

fun SignUpRequest.toSignUpCommand() =
    SignUpCommand(username.trim(), email.trim(), password)

fun User.toUserWithoutPasswordResponse() =
    UserSummaryResponse(id, username, email, createdAt!!, updatedAt!!)

fun SignInRequest.toSignInCommand() =
    SignInCommand(email.trim(), password)
