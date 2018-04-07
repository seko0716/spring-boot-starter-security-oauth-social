package com.seko0716.springboot.starter.oauth2.social

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@ConditionalOnClass(name = ["org.springframework.data.jpa.repository.config.EnableJpaRepositories"])
@EnableJpaRepositories(basePackages = ["com.seko0716.springboot.starter.oauth2.social.repository"])
class JpaConfiguration {
}