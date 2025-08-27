package com.rangjin.chatapi.adapter.out.persistence.user.repository

import com.rangjin.chatapi.adapter.out.persistence.user.entity.UserJpaEntity
import com.rangjin.chatapi.domain.user.model.User
import com.rangjin.chatapi.port.out.persistence.user.UserRepository
import org.springframework.stereotype.Component

@Component
class UserRepositoryAdapter (

    private val userJpaRepository: UserJpaRepository

): UserRepository {

    override fun save(user: User): User {
        val entity = user.toEntity()
        val saved = userJpaRepository.save(entity)
        return saved.toDomain()
    }

    override fun findByEmail(email: String): User? {
        return userJpaRepository.findByEmail(email)?.toDomain()
    }

    private fun UserJpaEntity.toDomain() = User(
        id = id, username = username, email = email, passwordHash = password
    )

    private fun User.toEntity() = UserJpaEntity(
        id = id, username = username, email = email, password = passwordHash
    )

}