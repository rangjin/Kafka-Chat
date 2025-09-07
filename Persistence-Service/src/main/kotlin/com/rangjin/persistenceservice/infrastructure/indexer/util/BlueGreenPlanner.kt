package com.rangjin.persistenceservice.infrastructure.indexer.util

import org.springframework.stereotype.Component

@Component
class BlueGreenPlanner(

    private val es: EsAdminClient

) {

    data class Plan(

        val active: String?,

        val standby: String

    )

    fun plan(blue: String, green: String, alias: String): Plan {
        val active = es.getAliasTargets(alias).firstOrNull()
        val standby =
            when (active) {
                blue -> green
                green -> blue
                else -> blue
            }
        return Plan(active, standby)
    }

}