package com.rangjin.chatapi.application.user.service

import com.rangjin.chatapi.application.user.port.out.PasswordHasher
import com.rangjin.chatapi.application.user.port.out.TokenProvider
import com.rangjin.chatapi.application.user.port.out.UserRepository
import com.rangjin.chatapi.application.user.port.`in`.AuthUseCase
import com.rangjin.chatapi.application.user.port.`in`.SignInUseCase
import com.rangjin.chatapi.application.user.port.`in`.SignUpUseCase
import com.rangjin.chatapi.common.error.CustomException
import com.rangjin.chatapi.common.error.ErrorCode
import com.rangjin.chatapi.domain.user.User
import com.rangjin.chatapi.application.user.dto.AuthPrincipal
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(

    private val userRepository: UserRepository,

    private val passwordHasher: PasswordHasher,

    private val tokenProvider: TokenProvider

) : SignUpUseCase, SignInUseCase, AuthUseCase {

    @Transactional
    override fun signUp(
        email: String,
        username: String,
        password: String
    ): User {
        val user = User(
            username = username,
            email = email,
            passwordHash = passwordHasher.hash(password)
        )

        return userRepository.save(user)
    }

    @Transactional(readOnly = true)
    override fun signIn(email: String, rawPassword: String): String {
        val user = userRepository.findByEmail(email)
            ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        if (passwordHasher.matches(rawPassword, user.passwordHash)) {
            return tokenProvider.generateToken(user.id!!, user.username)
        } else throw CustomException(ErrorCode.WRONG_PASSWORD)
    }

    override fun getPrincipal(token: String): AuthPrincipal? =
        tokenProvider.authenticateFromHeader(token)

}