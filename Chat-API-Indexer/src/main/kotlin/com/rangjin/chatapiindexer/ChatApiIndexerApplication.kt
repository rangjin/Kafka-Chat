package com.rangjin.chatapiindexer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class ProjectionServiceApplication

fun main(args: Array<String>) {
    runApplication<ProjectionServiceApplication>(*args)
}
