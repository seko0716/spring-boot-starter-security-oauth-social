package com.seko0716.springbootstartersecurityoauthvkgoogle.auth

import com.seko0716.springbootstartersecurityoauthvkgoogle.auth.extractors.VkPrincipalExtractor
import com.seko0716.springbootstartersecurityoauthvkgoogle.configurations.properties.VkProperties
import com.seko0716.springbootstartersecurityoauthvkgoogle.repository.IUserStorage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails

@Configuration
@ComponentScan(basePackages = ["com.seko0716.springbootstartersecurityoauthvkgoogle"])
class VkConfiguration {
    @Autowired
    private lateinit var userStorage: IUserStorage

    @Bean("vkClient")
    @ConfigurationProperties("vk.client")
    @ConditionalOnProperty(prefix = "vk.client", name = [
        "clientId", "clientSecret", "accessTokenUri", "userAuthorizationUri",
        "authenticationScheme", "clientAuthenticationScheme", "scope"
    ])
    fun vkClient(): AuthorizationCodeResourceDetails {
        return AuthorizationCodeResourceDetails()
    }

    @Bean("vkResource")
    @ConfigurationProperties("vk.resource")
    @ConditionalOnProperty(prefix = "vk.resource", name = ["userInfoUri"])
    fun vkResource(): ResourceServerProperties {
        return ResourceServerProperties()
    }

    @Bean("vk")
    @ConfigurationProperties("vk")
    @ConditionalOnProperty(prefix = "vk", name = ["loginUrl"])
    fun vk(): VkProperties {
        return VkProperties()
    }

    @Bean
    fun vkPrincipalExtractor(): VkPrincipalExtractor {
        return VkPrincipalExtractor(userStorage = userStorage)
    }

    @Bean("vkTokenProvider")
    fun vkTokenProvider(): AuthorizationCodeAccessTokenProvider {
        return VkAuthorizationCodeAccessTokenProvider()
    }


}