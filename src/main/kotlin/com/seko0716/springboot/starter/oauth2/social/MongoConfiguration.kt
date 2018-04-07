package com.seko0716.springboot.starter.oauth2.social

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@ConditionalOnClass(name = ["org.springframework.data.mongodb.repository.config.EnableMongoRepositories"])
@EnableMongoRepositories(basePackages = ["com.seko0716.springboot.starter.oauth2.social.repository"])
class MongoConfiguration {
}