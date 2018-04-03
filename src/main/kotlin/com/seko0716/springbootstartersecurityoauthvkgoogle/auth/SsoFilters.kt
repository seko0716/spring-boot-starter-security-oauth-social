package com.seko0716.springbootstartersecurityoauthvkgoogle.auth

import com.seko0716.springbootstartersecurityoauthvkgoogle.auth.extractors.AuthoritiesExtractorImpl
import com.seko0716.springbootstartersecurityoauthvkgoogle.repository.IUserStorage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter

@Configuration
@ComponentScan(basePackages = ["com.seko0716.springbootstartersecurityoauthvkgoogle"])
class SsoFilters {

    @Autowired
    private lateinit var userStorage: IUserStorage

    @Bean
    fun oauth2ClientFilterRegistration(filter: OAuth2ClientContextFilter): FilterRegistrationBean {
        val registration = FilterRegistrationBean()
        registration.filter = filter
        registration.order = -100
        return registration
    }

    @Bean
    fun authoritiesExtractor(): AuthoritiesExtractor {
        return AuthoritiesExtractorImpl(userStorage = userStorage)
    }


}