package com.rangjin.chatapi.infrastructure.persistence.channel.repository

import com.rangjin.chatapi.infrastructure.persistence.channel.entity.ChannelJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ChannelJpaRepository : JpaRepository<ChannelJpaEntity, Long> {

    @Query(
        """
        select distinct c
        from ChannelJpaEntity c
        join c.memberships m
        where m.user.id = :userId
    """
    )
    fun findAllByUserId(userId: Long): List<ChannelJpaEntity>

}