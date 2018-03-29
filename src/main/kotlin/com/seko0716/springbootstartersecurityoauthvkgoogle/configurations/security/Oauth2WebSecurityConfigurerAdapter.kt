package com.seko0716.springbootstartersecurityoauthvkgoogle.configurations.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
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
import com.seko0716.springbootstartersecurityoauthvkgoogle.repository.UserRepository
import com.seko0716.springbootstartersecurityoauthvkgoogle.service.UserDetailServiceImpl
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import javax.servlet.Filter

@Configuration
@EnableJpaRepositories(basePackages = ["com.seko0716.springbootstartersecurityoauthvkgoogle.repository"])
@EnableMongoRepositories(basePackages = ["com.seko0716.springbootstartersecurityoauthvkgoogle.repository"])
@ComponentScan(basePackages = ["com.seko0716.springbootstartersecurityoauthvkgoogle"])
class Oauth2WebSecurityConfigurerAdapter : WebSecurityConfigurerAdapter() {
    @Autowired
    private lateinit var oAuth2ClientAuthenticationProcessingFilters: List<OAuth2ClientAuthenticationProcessingFilter>
    @Autowired
    private lateinit var userRepository: UserRepository

    private fun ssoFilter(): Filter {
        val filter = CompositeFilter()
        filter.setFilters(oAuth2ClientAuthenticationProcessingFilters)

        return filter
    }

    @Bean
    override fun userDetailsService(): UserDetailsService {
        return UserDetailServiceImpl(userRepository)
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