package com.seko0716.springboot.starter.oauth2.social.security

import com.seko0716.springboot.starter.oauth2.social.repository.IUserStorage
import com.seko0716.springboot.starter.oauth2.social.service.UserDetailServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.web.filter.CompositeFilter
import javax.servlet.Filter

@Configuration
@ComponentScan(basePackages = ["com.seko0716.springboot.starter.oauth2.social"])
class Oauth2WebSecurityConfigurerAdapter : WebSecurityConfigurerAdapter() {
    @Autowired(required = false)
    var oAuth2ClientAuthenticationProcessingFilters: List<OAuth2ClientAuthenticationProcessingFilter> = listOf()
    @Autowired
    private lateinit var userStorage: IUserStorage

    private fun ssoFilter(): Filter {
        val filter = CompositeFilter()
        filter.setFilters(oAuth2ClientAuthenticationProcessingFilters)

        return filter
    }

    @Bean
    override fun userDetailsService(): UserDetailsService {
        return UserDetailServiceImpl(userStorage)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService())
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.addFilterBefore(ssoFilter(), BasicAuthenticationFilter::class.java)
                .userDetailsService(userDetailsService())
                .authenticationProvider(authProvider())
    }
}