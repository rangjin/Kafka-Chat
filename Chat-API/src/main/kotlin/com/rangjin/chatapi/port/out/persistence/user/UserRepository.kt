package com.rangjin.chatapi.port.out.persistence.user

import com.rangjin.chatapi.domain.user.model.User

interface UserRepository {

    fun save(user: User): User

    fun findById(id: Long): User?

    fun findByEmail(email: String): User?

}