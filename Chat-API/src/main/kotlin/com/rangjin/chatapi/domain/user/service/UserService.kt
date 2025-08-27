package com.rangjin.chatapi.domain.user.service

import com.rangjin.chatapi.adapter.out.security.jwt.JwtTokenProvider
import com.rangjin.chatapi.domain.user.model.User
import com.rangjin.chatapi.port.`in`.user.command.SignInCommand
import com.rangjin.chatapi.port.`in`.user.command.SignUpCommand
import com.rangjin.chatapi.port.`in`.user.usecase.SignInUseCase
import com.rangjin.chatapi.port.`in`.user.usecase.SignUpUseCase
import com.rangjin.chatapi.port.out.persistence.user.UserRepository
import com.rangjin.chatapi.port.out.security.PasswordHasher
import org.springframework.stereotype.Service

@Service
class UserService (

    private val userRepository: UserRepository,

    private val passwordHasher: PasswordHasher,

    private val jwtTokenProvider: JwtTokenProvider

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
        val user = userRepository.findByEmail(signInCommand.email) ?: throw Exception()

        if (passwordHasher.matches(signInCommand.rawPassword, user.passwordHash)) {
            return jwtTokenProvider.generateToken(user.id!!, user.username)
        } else throw Exception()
    }

}