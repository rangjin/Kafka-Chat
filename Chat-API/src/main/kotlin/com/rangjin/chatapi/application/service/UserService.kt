package com.rangjin.chatapi.application.service

import com.rangjin.chatapi.application.port.`in`.user.SignInUseCase
import com.rangjin.chatapi.application.port.`in`.user.SignUpUseCase
import com.rangjin.chatapi.application.port.out.user.PasswordHasher
import com.rangjin.chatapi.application.port.out.user.TokenProvider
import com.rangjin.chatapi.application.port.out.user.UserRepository
import com.rangjin.chatapi.common.error.CustomException
import com.rangjin.chatapi.common.error.ErrorCode
import com.rangjin.chatapi.domain.user.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(

    private val userRepository: UserRepository,

    private val passwordHasher: PasswordHasher,

    private val tokenProvider: TokenProvider

) : SignUpUseCase, SignInUseCase {

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

}