package com.seko0716.springboot.starter.oauth2.social.auth.vk

import com.seko0716.springboot.starter.oauth2.social.auth.extractors.AuthoritiesExtractorImpl
import com.seko0716.springboot.starter.oauth2.social.auth.extractors.OAuth2UserService
import com.seko0716.springboot.starter.oauth2.social.auth.extractors.VkPrincipalExtractor
import com.seko0716.springboot.starter.oauth2.social.infrastructure.properties.VkClientProperty
import com.seko0716.springboot.starter.oauth2.social.infrastructure.properties.VkProperties
import com.seko0716.springboot.starter.oauth2.social.infrastructure.properties.VkResourceProperties
import com.seko0716.springboot.starter.oauth2.social.repository.IUserStorage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider
import java.util.*

@Configuration
@ComponentScan(basePackages = ["com.seko0716.springboot.starter.oauth2.social"])
@EnableConfigurationProperties(value = [
    VkClientProperty::class,
    VkProperties::class,
    VkResourceProperties::class
])
@ConditionalOnProperty(prefix = "vk", name = [
    "client.clientId", "client.clientSecret", "client.accessTokenUri", "client.userAuthorizationUri",
    "client.authenticationScheme", "client.clientAuthenticationScheme", "client.scope", "resource.userInfoUri"
])
class VkConfiguration @Autowired constructor(var userStorage: IUserStorage,
                                             var oauth2ClientContext: OAuth2ClientContext,
                                             var oAuth2UserService: OAuth2UserService) {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)
    
    @Bean
    fun vkFilter(vkResource: VkResourceProperties,
                 vkClient: VkClientProperty): OAuth2ClientAuthenticationProcessingFilter {
        val vkFilter = OAuth2ClientAuthenticationProcessingFilter(vk().loginUrl)
        val vkTemplate = OAuth2RestTemplate(vkClient, oauth2ClientContext)
        vkFilter.setRestTemplate(vkTemplate)
        vkTemplate.setAccessTokenProvider(AccessTokenProviderChain(Arrays.asList(
                vkTokenProvider(), ImplicitAccessTokenProvider(),
                ResourceOwnerPasswordAccessTokenProvider(), ClientCredentialsAccessTokenProvider())))
        val tokenServices = UserInfoTokenServices(vkResource.userInfoUri, vkClient.clientId)
        tokenServices.setRestTemplate(vkTemplate)
        tokenServices.setTokenType("code")
        tokenServices.setAuthoritiesExtractor(vkAuthoritiesExtractor())
        tokenServices.setPrincipalExtractor(vkPrincipalExtractor())
        vkFilter.setTokenServices(tokenServices)
        log.trace("init google oauth2 filter")
        return vkFilter
    }

    @Bean("vkClient")
    @ConfigurationProperties("vk.client")
    fun vkClient(): VkClientProperty {
        return VkClientProperty()
    }

    @Bean("vkResource")
    @ConfigurationProperties("vk.resource")
    fun vkResource(): VkResourceProperties {
        return VkResourceProperties()
    }

    @Bean("vk")
    @ConfigurationProperties("vk")
    fun vk(): VkProperties {
        return VkProperties()
    }

    @Bean
    fun vkPrincipalExtractor(): VkPrincipalExtractor {
        return VkPrincipalExtractor(userStorage = userStorage, vk = vk(), OAuth2UserService = oAuth2UserService)
    }

    @Bean("vkTokenProvider")
    fun vkTokenProvider(): AuthorizationCodeAccessTokenProvider {
        return VkAuthorizationCodeAccessTokenProvider()
    }

    @Bean
    fun vkAuthoritiesExtractor(): AuthoritiesExtractor {
        return AuthoritiesExtractorImpl(userStorage = userStorage, OAuth2UserService = oAuth2UserService, idField = vk().idField)
    }
}