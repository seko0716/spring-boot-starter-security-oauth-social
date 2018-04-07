package com.seko0716.springboot.starter.oauth2.social

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import javax.annotation.PostConstruct

@ConditionalOnClass(name = ["org.springframework.data.mongodb.repository.config.EnableMongoRepositories"])
@EnableMongoRepositories(basePackages = ["com.seko0716.springboot.starter.oauth2.social.repository"])
@Configuration
class MongoConfiguration {
    @PostConstruct
    fun init() {
        println("MONGO")
    }
}