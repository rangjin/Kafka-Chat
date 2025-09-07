package com.rangjin.projectionservice.infrastructure.indexer.index.user

import com.rangjin.projectionservice.infrastructure.indexer.config.IndexSource
import com.rangjin.projectionservice.infrastructure.persistence.user.entity.UserJpaEntity
import com.rangjin.projectionservice.infrastructure.persistence.user.repository.UserJpaRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class UserIndexSource(

    private val userJpaRepository: UserJpaRepository

) : IndexSource<UserJpaEntity, UserDoc> {

    override val name: String = "users"

    override val docClass: KClass<UserDoc> = UserDoc::class

    override fun fetchPage(
        afterId: Long,
        pageable: Pageable
    ): List<UserJpaEntity> =
        userJpaRepository.findByIdGreaterThanOrderByIdAsc(afterId, pageable)

    override fun entityId(entity: UserJpaEntity): Long =
        entity.id!!

    override fun toDoc(entity: UserJpaEntity): UserDoc =
        UserDoc(
            entity.id!!,
            entity.username,
            entity.email,
            entity.password,
            entity.createdAt,
            entity.updatedAt
        )

}