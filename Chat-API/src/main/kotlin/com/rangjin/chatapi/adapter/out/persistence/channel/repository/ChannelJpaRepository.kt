package com.rangjin.chatapi.adapter.out.persistence.channel.repository

import com.rangjin.chatapi.adapter.out.persistence.channel.entity.ChannelJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ChannelJpaRepository : JpaRepository<ChannelJpaEntity, Long>