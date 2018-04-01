package com.seko0716.springbootstartersecurityoauthvkgoogle

import com.seko0716.springbootstartersecurityoauthvkgoogle.repository.IUserStorage
import com.seko0716.springbootstartersecurityoauthvkgoogle.repository.UserRepositoryDefault
import com.seko0716.springbootstartersecurityoauthvkgoogle.repository.UserStorage
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client

@Configuration
@ComponentScan(basePackages = ["com.seko0716.springbootstartersecurityoauthvkgoogle"])
@EnableOAuth2Client
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties
class SpringSecurityOauth2VkGoogleAutoConfiguration {

    @Bean
    fun userStorage(userRepository: UserRepositoryDefault): IUserStorage {
        return UserStorage(userRepository)
    }

}