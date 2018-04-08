package com.seko0716.springboot.starter.oauth2.social

import com.seko0716.springboot.starter.oauth2.social.auth.extractors.OAuth2UserService
import com.seko0716.springboot.starter.oauth2.social.repository.IUserStorage
import com.seko0716.springboot.starter.oauth2.social.repository.UserRepositoryDefault
import com.seko0716.springboot.starter.oauth2.social.repository.UserStorage
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client

@Configuration
@ComponentScan(basePackages = ["com.seko0716.springboot.starter.oauth2.social"])
@EnableOAuth2Client
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties
class SpringSecurityOauth2VkGoogleAutoConfiguration {

    @Bean
    fun userStorage(userRepository: UserRepositoryDefault): IUserStorage {
        return UserStorage(userRepository)
    }

    @Bean
    fun oAuth2UserService(): OAuth2UserService {
        return OAuth2UserService()
    }
}