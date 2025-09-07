package com.rangjin.persistenceservice.infrastructure.persistence.user.entity

import com.rangjin.persistenceservice.infrastructure.persistence.common.BaseTimeEntity
import com.rangjin.persistenceservice.infrastructure.persistence.membership.entity.MembershipJpaEntity
import jakarta.persistence.*

@Entity
@Table(name = "user")
class UserJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(unique = true, nullable = false)
    val username: String,

    @Column(unique = true, nullable = false)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val memberships: MutableSet<MembershipJpaEntity> = mutableSetOf()

) : BaseTimeEntity()