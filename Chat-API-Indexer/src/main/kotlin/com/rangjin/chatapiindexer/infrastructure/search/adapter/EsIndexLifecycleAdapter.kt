package com.rangjin.chatapiindexer.infrastructure.search.adapter

import com.rangjin.chatapiindexer.application.port.out.DocMapper
import com.rangjin.chatapiindexer.application.port.out.IndexLifecyclePort
import com.rangjin.chatapiindexer.application.vo.BlueGreenPlan
import com.rangjin.chatapiindexer.infrastructure.search.util.EsAdminClient
import org.springframework.stereotype.Component

@Component
class EsIndexLifecycleAdapter(

    private val es: EsAdminClient,

) : IndexLifecyclePort {

    private var plan: BlueGreenPlan? = null

    override fun fullIndexStart(plan: BlueGreenPlan) {
        this.plan = plan
    }

    override fun fullIndexInProgress(): BlueGreenPlan? =
        plan

    override fun fullIndexFinish() {
        plan = null
    }


    override fun plan(
        blue: String,
        green: String,
        alias: String
    ): BlueGreenPlan {
        val active = es.getAliasTargets(alias).firstOrNull()
        val standby = when (active) {
            blue -> green
            green -> blue
            else -> blue
        }
        return BlueGreenPlan(alias = alias, active = active, standby = standby)
    }

    override fun <D : Any> recreateIndex(
        index: String,
        mapper: DocMapper<*, D>,
        settings: Map<String, Any>
    ) =
        es.recreate(index, mapper.docClass.java, settings)

    override fun refresh(index: String) =
        es.refresh(index)

    override fun putIndexSettings(index: String, settings: Map<String, Any>) {
        es.putIndexSettings(index, settings)
    }

    override fun switchAlias(alias: String, removeIndex: String?, addIndex: String) =
        es.switchAlias(alias, removeIndex, addIndex)

}