package com.rangjin.chatapiindexer.presentation.scheduler.full

import com.rangjin.chatapiindexer.application.port.`in`.FullIndexer
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class FullIndexScheduler(

    private val indexer: FullIndexer

) {

    @Scheduled(cron = "0 0 3 * * *")
    fun runAll() {
        indexer.runAll()
    }

}