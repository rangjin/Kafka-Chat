package com.rangjin.chatapi.infrastructure.persistence.user.repository

import com.rangjin.chatapi.domain.user.User
import com.rangjin.chatapi.application.port.out.user.UserRepository
import com.rangjin.chatapi.infrastructure.persistence.user.mapper.UserMapper
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class UserRepositoryAdapter(

    private val userJpaRepository: UserJpaRepository

) : UserRepository {

    override fun save(user: User): User {
        val entity = UserMapper.toJpa(user)
        val saved = userJpaRepository.save(entity)
        return UserMapper.toDomain(saved)
    }

    override fun existsById(id: Long): Boolean =
        userJpaRepository.existsById(id)

    override fun findById(id: Long): User? {
        val entity = userJpaRepository.findByIdOrNull(id) ?: return null
        return UserMapper.toDomain(entity)
    }

    override fun findByEmail(email: String): User? {
        val entity = userJpaRepository.findByEmail(email) ?: return null
        return UserMapper.toDomain(entity)
    }

    override fun findByEmailIn(emails: List<String>): List<User> =
        userJpaRepository.findByEmailIn(emails)
            .map { UserMapper.toDomain(it) }

}