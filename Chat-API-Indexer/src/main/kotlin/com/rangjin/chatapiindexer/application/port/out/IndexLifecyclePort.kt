package com.rangjin.chatapiindexer.application.port.out

import com.rangjin.chatapiindexer.application.vo.BlueGreenPlan

interface IndexLifecyclePort {

    fun fullIndexStart(plan: BlueGreenPlan)

    fun fullIndexInProgress(): BlueGreenPlan?

    fun fullIndexFinish()

    fun plan(blue: String, green: String, alias: String): BlueGreenPlan

    fun <D : Any> recreateIndex(
        index: String,
        mapper: DocMapper<*, D>
    )

    fun refresh(index: String)

    fun putIndexSettings(index: String)

    fun switchAlias(alias: String, removeIndex: String?, addIndex: String)

}