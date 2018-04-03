package com.seko0716.springbootstartersecurityoauthvkgoogle.auth

import com.seko0716.springbootstartersecurityoauthvkgoogle.auth.extractors.GooglePrincipalExtractor
import com.seko0716.springbootstartersecurityoauthvkgoogle.configurations.properties.GoogleProperties
import com.seko0716.springbootstartersecurityoauthvkgoogle.repository.IUserStorage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails

@Configuration
@ComponentScan(basePackages = ["com.seko0716.springbootstartersecurityoauthvkgoogle"])
class GoogleConfiguration {
    @Autowired
    private lateinit var userStorage: IUserStorage

    @Bean("googleClient")
    @ConfigurationProperties("google.client")
    @ConditionalOnProperty(prefix = "google.client", name = [
        "clientId", "clientSecret", "accessTokenUri", "userAuthorizationUri",
        "clientAuthenticationScheme", "scope"
    ])
    fun googleClient(): AuthorizationCodeResourceDetails {
        return AuthorizationCodeResourceDetails()
    }

    @Bean("googleResource")
    @ConfigurationProperties("google.resource")
    @ConditionalOnProperty(prefix = "google.resource", name = ["userInfoUri"])
    fun googleResource(): ResourceServerProperties {
        return ResourceServerProperties()
    }

    @Bean("google")
    @ConfigurationProperties("google")
    @ConditionalOnProperty(prefix = "google", name = ["loginUrl"])
    fun google(): GoogleProperties {
        return GoogleProperties()
    }

    @Bean("googlePrincipalExtractor")
    fun googlePrincipalExtractor(): GooglePrincipalExtractor {
        return GooglePrincipalExtractor(userStorage = userStorage)
    }


}