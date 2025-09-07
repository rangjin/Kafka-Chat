package com.rangjin.persistenceservice.infrastructure.indexer.index.membership

import com.rangjin.persistenceservice.infrastructure.indexer.config.IndexSource
import com.rangjin.persistenceservice.infrastructure.persistence.membership.entity.MembershipJpaEntity
import com.rangjin.persistenceservice.infrastructure.persistence.membership.repository.MembershipJpaRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class MembershipIndexSource(

    private val membershipJpaRepository: MembershipJpaRepository

): IndexSource<MembershipJpaEntity, MembershipDoc> {

    override val name: String = "memberships"

    override val docClass: KClass<MembershipDoc> = MembershipDoc::class

    override fun fetchPage(
        afterId: Long,
        pageable: Pageable
    ): List<MembershipJpaEntity> =
        membershipJpaRepository.findByIdGreaterThanOrderByIdAsc(afterId, pageable)

    override fun entityId(entity: MembershipJpaEntity): Long =
        entity.id!!

    override fun toDoc(entity: MembershipJpaEntity): MembershipDoc =
        MembershipDoc(
            entity.id!!,
            entity.user.id!!,
            entity.channel.id!!,
            entity.role,
            entity.joinedAt,
            entity.createdAt,
            entity.updatedAt
        )

}