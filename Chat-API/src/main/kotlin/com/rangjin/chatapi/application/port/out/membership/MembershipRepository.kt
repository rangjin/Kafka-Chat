package com.rangjin.chatapi.application.port.out.membership

import com.rangjin.chatapi.domain.membership.Membership

interface MembershipRepository {

    fun save(membership: Membership): Membership

    fun saveAll(memberships: List<Membership>): List<Membership>

    fun delete(membership: Membership)

}