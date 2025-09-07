package com.rangjin.projectionservice.infrastructure.persistence.membership.repository

import com.rangjin.projectionservice.infrastructure.persistence.membership.entity.MembershipJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MembershipJpaRepository : JpaRepository<MembershipJpaEntity, Long> {
}