package com.rangjin.chatapi.domain.user.service

import com.rangjin.chatapi.common.error.CustomException
import com.rangjin.chatapi.common.error.ErrorCode
import com.rangjin.chatapi.domain.user.model.User
import com.rangjin.chatapi.domain.user.port.`in`.command.SignInCommand
import com.rangjin.chatapi.domain.user.port.`in`.command.SignUpCommand
import com.rangjin.chatapi.domain.user.port.`in`.usecase.SignInUseCase
import com.rangjin.chatapi.domain.user.port.`in`.usecase.SignUpUseCase
import com.rangjin.chatapi.domain.user.port.out.persistence.UserRepository
import com.rangjin.chatapi.domain.user.port.out.auth.PasswordHasher
import com.rangjin.chatapi.domain.user.port.out.auth.TokenProvider
import org.springframework.stereotype.Service

@Service
class UserService (

    private val userRepository: UserRepository,

    private val passwordHasher: PasswordHasher,

    private val tokenProvider: TokenProvider

): SignUpUseCase, SignInUseCase {

    override fun signUp(command: SignUpCommand): User {
        val user = User(
            username = command.username,
            email = command.email,
            passwordHash = passwordHasher.hash(command.rawPassword),
        )

        return userRepository.save(user)
    }

    override fun signIn(signInCommand: SignInCommand): String {
        val user = userRepository.findByEmail(signInCommand.email)
            ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        if (passwordHasher.matches(signInCommand.rawPassword, user.passwordHash)) {
            return tokenProvider.generateToken(user.id!!, user.username)
        } else throw CustomException(ErrorCode.WRONG_PASSWORD)
    }

}