package com.rangjin.chatapi.infrastructure.persistence.membership.repository

import com.rangjin.chatapi.application.port.out.membership.MembershipRepository
import com.rangjin.chatapi.domain.membership.Membership
import com.rangjin.chatapi.infrastructure.persistence.channel.entity.ChannelJpaEntity
import com.rangjin.chatapi.infrastructure.persistence.membership.mapper.MembershipMapper
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Component

@Component
class MembershipRepositoryAdapter(

    private val membershipJpaRepository: MembershipJpaRepository,

    private val em: EntityManager

) : MembershipRepository {

    override fun save(membership: Membership): Membership {
        val membershipJpa = MembershipMapper.toJpa(
            membership
        ) {
            em.getReference(ChannelJpaEntity::class.java, it)
        }

        return MembershipMapper.toDomain(
            membershipJpaRepository.save(membershipJpa)
        )
    }

    override fun saveAll(memberships: List<Membership>): List<Membership> {
        val channelRef: (Long) -> ChannelJpaEntity = {
            em.getReference(ChannelJpaEntity::class.java, it)
        }

        val membershipJpaEntities = memberships.map {
            MembershipMapper.toJpa(it, channelRef)
        }

        return membershipJpaRepository.saveAll(membershipJpaEntities).map {
            MembershipMapper.toDomain(it)
        }
    }

    override fun delete(membership: Membership) {
        val membershipJpa = MembershipMapper.toJpa(
            membership,
            {
                em.getReference(ChannelJpaEntity::class.java, it)
            }
        )

        membershipJpaRepository.delete(membershipJpa)
    }

}