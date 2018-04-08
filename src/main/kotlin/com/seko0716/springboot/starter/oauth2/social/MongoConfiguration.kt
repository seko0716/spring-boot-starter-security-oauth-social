package com.seko0716.springboot.starter.oauth2.social

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import javax.annotation.PostConstruct

@ConditionalOnClass(name = ["org.springframework.data.mongodb.repository.config.EnableMongoRepositories"])
@EnableMongoRepositories(basePackages = ["com.seko0716.springboot.starter.oauth2.social.repository"])
@Configuration
class MongoConfiguration {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @PostConstruct
    fun init() {
        log.trace("Init jpa repositories")
    }
}