package com.seko0716.springboot.starter.oauth2.social.auth

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter

@Configuration
@ComponentScan(basePackages = ["com.seko0716.springboot.starter.oauth2.social"])
class SsoFilters {

    @Bean
    fun oauth2ClientFilterRegistration(filter: OAuth2ClientContextFilter): FilterRegistrationBean {
        val registration = FilterRegistrationBean()
        registration.filter = filter
        registration.order = -100
        return registration
    }


}