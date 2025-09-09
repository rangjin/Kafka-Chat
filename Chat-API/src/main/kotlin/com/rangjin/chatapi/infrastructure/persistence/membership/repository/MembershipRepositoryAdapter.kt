package com.rangjin.chatapi.infrastructure.persistence.membership.repository

import com.rangjin.chatapi.application.port.out.membership.MembershipRepository
import com.rangjin.chatapi.domain.membership.Membership
import com.rangjin.chatapi.infrastructure.persistence.channel.entity.ChannelJpaEntity
import com.rangjin.chatapi.infrastructure.persistence.membership.mapper.MembershipMapper
import com.rangjin.chatapi.infrastructure.persistence.user.entity.UserJpaEntity
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Component

@Component
class MembershipRepositoryAdapter(

    private val membershipJpaRepository: MembershipJpaRepository,

    private val em: EntityManager

) : MembershipRepository {

    private val userRef: (Long) -> UserJpaEntity = {
        em.getReference(UserJpaEntity::class.java, it)
    }

    private val channelRef: (Long) -> ChannelJpaEntity = {
        em.getReference(ChannelJpaEntity::class.java, it)
    }

    override fun save(membership: Membership): Membership =
        MembershipMapper.toDomain(
            membershipJpaRepository.save(MembershipMapper.toJpa(membership))
        )

    override fun saveAll(memberships: List<Membership>): List<Membership> {
        val membershipJpaEntities = memberships.map {
            MembershipMapper.toJpa(it)
        }

        return membershipJpaRepository.saveAll(membershipJpaEntities).map {
            MembershipMapper.toDomain(it)
        }
    }

    override fun existsByUserIdAndChannelId(userId: Long, channelId: Long): Boolean =
        membershipJpaRepository.existsByUserAndChannel(userRef(userId), channelRef(channelId))

    override fun findByUserIdAndChannelId(
        userId: Long,
        channelId: Long
    ): Membership? =
        membershipJpaRepository.findByUserAndChannel(userRef(userId), channelRef(channelId))?.let {
            MembershipMapper.toDomain(it)
        }

    override fun findAllByUserId(userId: Long): List<Membership> =
        membershipJpaRepository.findAllByUser(userRef(userId))
            .map { MembershipMapper.toDomain(it) }

    override fun findAllByChannelId(channelId: Long): List<Membership> =
        membershipJpaRepository.findAllByChannel(channelRef(channelId))
            .map { MembershipMapper.toDomain(it) }

    override fun delete(membership: Membership) =
        membershipJpaRepository.delete(MembershipMapper.toJpa(membership))

}