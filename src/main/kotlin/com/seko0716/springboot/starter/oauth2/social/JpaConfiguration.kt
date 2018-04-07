package com.seko0716.springboot.starter.oauth2.social

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import javax.annotation.PostConstruct

@ConditionalOnClass(name = ["org.springframework.data.jpa.repository.config.EnableJpaRepositories"])
@EnableJpaRepositories(basePackages = ["com.seko0716.springboot.starter.oauth2.social.repository"])
@Configuration
class JpaConfiguration {
    @PostConstruct
    fun init(): Unit {
        println("JPA")
    }
}