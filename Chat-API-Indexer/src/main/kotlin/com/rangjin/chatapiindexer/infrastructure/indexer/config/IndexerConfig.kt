package com.rangjin.chatapiindexer.infrastructure.indexer.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(IndexProps::class)
class IndexerConfig