package com.rangjin.chatapiindexer.application.port.out

import com.rangjin.chatapiindexer.application.vo.BlueGreenPlan

interface IndexLifecyclePort {

    fun plan(blue: String, green: String, alias: String): BlueGreenPlan

    fun <D : Any> recreateIndexWithMapper(
        index: String,
        mapper: DocMapper<*, D>,
        settings: Map<String, Any>
    )

    fun putIndexSettings(index: String, settings: Map<String, Any>)

    fun switchAlias(alias: String, removeIndex: String?, addIndex: String)

    fun refresh(index: String)

}