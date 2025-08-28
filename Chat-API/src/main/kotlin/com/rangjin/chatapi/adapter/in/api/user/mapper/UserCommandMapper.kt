package com.rangjin.chatapi.adapter.`in`.api.user.mapper

import com.rangjin.chatapi.adapter.`in`.api.user.dto.request.SignInRequest
import com.rangjin.chatapi.adapter.`in`.api.user.dto.request.SignUpRequest
import com.rangjin.chatapi.adapter.`in`.api.user.dto.response.UserWithoutPasswordResponse
import com.rangjin.chatapi.domain.user.model.User
import com.rangjin.chatapi.port.`in`.user.command.SignInCommand
import com.rangjin.chatapi.port.`in`.user.command.SignUpCommand

fun SignUpRequest.toSignUpCommand() =
    SignUpCommand(username.trim(), email.trim(), password)

fun User.toUserWithoutPasswordResponse() =
    UserWithoutPasswordResponse(id, username, email, createdAt!!, updatedAt!!)

fun SignInRequest.toSignInCommand() =
    SignInCommand(email.trim(), password)
