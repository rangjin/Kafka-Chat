package com.rangjin.chatapi.adapter.out.persistence.user.repository

import com.rangjin.chatapi.adapter.out.persistence.user.mapper.toDomain
import com.rangjin.chatapi.adapter.out.persistence.user.mapper.toEntity
import com.rangjin.chatapi.domain.user.model.User
import com.rangjin.chatapi.domain.user.port.out.persistence.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class UserRepositoryAdapter(

    private val userJpaRepository: UserJpaRepository

) : UserRepository {

    override fun save(user: User): User {
        val entity = user.toEntity()
        val saved = userJpaRepository.save(entity)
        return saved.toDomain()
    }

    override fun findById(id: Long): User? {
        return userJpaRepository.findByIdOrNull(id)?.toDomain()
    }

    override fun findByEmail(email: String): User? {
        return userJpaRepository.findByEmail(email)?.toDomain()
    }


}