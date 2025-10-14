package com.rangjin.chatapi.application.user.port.out

import com.rangjin.chatapi.domain.user.User

interface UserRepository {

    fun save(user: User): User

    fun existsById(id: Long): Boolean

    fun findById(id: Long): User?

    fun findByEmail(email: String): User?

    fun findByEmailIn(emails: List<String>): List<User>

}