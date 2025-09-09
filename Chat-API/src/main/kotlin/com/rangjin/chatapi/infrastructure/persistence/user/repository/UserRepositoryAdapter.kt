package com.rangjin.chatapi.infrastructure.persistence.user.repository

import com.rangjin.chatapi.application.port.out.user.UserRepository
import com.rangjin.chatapi.domain.user.User
import com.rangjin.chatapi.infrastructure.persistence.user.mapper.UserMapper
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class UserRepositoryAdapter(

    private val userJpaRepository: UserJpaRepository

) : UserRepository {

    override fun save(user: User): User =
        UserMapper.toDomain(
            userJpaRepository.save(
                UserMapper.toJpa(user)
            )
        )

    override fun existsById(id: Long): Boolean =
        userJpaRepository.existsById(id)

    override fun findById(id: Long): User? =
        userJpaRepository.findByIdOrNull(id)?.let { UserMapper.toDomain(it) }

    override fun findByEmail(email: String): User? =
        userJpaRepository.findByEmail(email)?.let { UserMapper.toDomain(it) }

    override fun findByEmailIn(emails: List<String>): List<User> =
        userJpaRepository.findByEmailIn(emails)
            .map { UserMapper.toDomain(it) }

}