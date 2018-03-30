package com.seko0716.springbootstartersecurityoauthvkgoogle

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootConfiguration
class Stopper {
}