package com.rangjin.chatapiindexer.application.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(IndexProps::class)
class IndexPropsConfig