package com.rangjin.chatapi.domain.user.port.out.persistence

import com.rangjin.chatapi.domain.user.model.User

interface UserRepository {

    fun save(user: User): User

    fun existsById(id: Long): Boolean

    fun findById(id: Long): User?

    fun findByEmail(email: String): User?

}