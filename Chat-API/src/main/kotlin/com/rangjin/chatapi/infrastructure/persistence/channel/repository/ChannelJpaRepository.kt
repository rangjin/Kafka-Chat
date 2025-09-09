package com.rangjin.chatapi.infrastructure.persistence.channel.repository

import com.rangjin.chatapi.infrastructure.persistence.channel.entity.ChannelJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ChannelJpaRepository : JpaRepository<ChannelJpaEntity, Long>