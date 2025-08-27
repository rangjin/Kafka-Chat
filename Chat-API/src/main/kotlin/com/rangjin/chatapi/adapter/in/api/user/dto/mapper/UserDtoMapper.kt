package com.rangjin.chatapi.adapter.`in`.api.user.dto.mapper

import com.rangjin.chatapi.adapter.`in`.api.user.dto.request.SignInRequest
import com.rangjin.chatapi.adapter.`in`.api.user.dto.request.SignUpRequest
import com.rangjin.chatapi.adapter.`in`.api.user.dto.response.SignUpResponse
import com.rangjin.chatapi.domain.user.model.User
import com.rangjin.chatapi.port.`in`.user.command.SignInCommand
import com.rangjin.chatapi.port.`in`.user.command.SignUpCommand
import org.springframework.stereotype.Component

@Component
class UserDtoMapper {

    fun toSignUpCommand(req: SignUpRequest) =
        SignUpCommand(req.username.trim(), req.email.trim(), req.password)

    fun toSignUpResponse(user: User) =
        SignUpResponse(user.id, user.username, user.email)

    fun toSignInCommand(req: SignInRequest) =
        SignInCommand(req.email.trim(), req.password)

}