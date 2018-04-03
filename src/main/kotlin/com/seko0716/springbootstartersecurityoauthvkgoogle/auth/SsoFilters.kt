package com.seko0716.springbootstartersecurityoauthvkgoogle.auth

import com.seko0716.springbootstartersecurityoauthvkgoogle.auth.extractors.AuthoritiesExtractorImpl
import com.seko0716.springbootstartersecurityoauthvkgoogle.auth.extractors.GooglePrincipalExtractor
import com.seko0716.springbootstartersecurityoauthvkgoogle.auth.extractors.VkPrincipalExtractor
import com.seko0716.springbootstartersecurityoauthvkgoogle.configurations.properties.*
import com.seko0716.springbootstartersecurityoauthvkgoogle.repository.IUserStorage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider
import java.util.*

@Configuration
@ComponentScan(basePackages = ["com.seko0716.springbootstartersecurityoauthvkgoogle"])
@EnableConfigurationProperties(value = [GoogleClientProperty::class,
    GoogleProperties::class,
    GoogleResourceProperties::class,
    VkClientProperty::class,
    VkProperties::class,
    VkResourceProperties::class])
class SsoFilters {

    @Autowired
    private lateinit var oauth2ClientContext: OAuth2ClientContext
    @Autowired
    private lateinit var userStorage: IUserStorage

    @Bean
    @ConditionalOnBean(name = ["googleClient", "googleResource", "google", "authoritiesExtractor"])
    fun googleFilter(): OAuth2ClientAuthenticationProcessingFilter {
        val googleFilter = OAuth2ClientAuthenticationProcessingFilter(google().loginUrl)
        val googleTemplate = OAuth2RestTemplate(googleClient(), oauth2ClientContext)
        googleFilter.setRestTemplate(googleTemplate)
        val tokenServices = UserInfoTokenServices(googleResource().userInfoUri, googleClient().clientId)
        tokenServices.setRestTemplate(googleTemplate)
        tokenServices.setAuthoritiesExtractor(authoritiesExtractor())
        tokenServices.setPrincipalExtractor(googlePrincipalExtractor())
        googleFilter.setTokenServices(tokenServices)
        return googleFilter
    }

    @Bean
    @ConditionalOnBean(name = ["vkClient", "vkResource", "vk", "authoritiesExtractor"])
    fun vkFilter(): OAuth2ClientAuthenticationProcessingFilter {
        val vkFilter = OAuth2ClientAuthenticationProcessingFilter(vk().loginUrl)
        val vkTemplate = OAuth2RestTemplate(vkClient(), oauth2ClientContext)
        vkFilter.setRestTemplate(vkTemplate)
        vkTemplate.setAccessTokenProvider(AccessTokenProviderChain(Arrays.asList(
                tokenProvider(), ImplicitAccessTokenProvider(),
                ResourceOwnerPasswordAccessTokenProvider(), ClientCredentialsAccessTokenProvider())))
        val tokenServices = UserInfoTokenServices(vkResource().userInfoUri, vkClient().clientId)
        tokenServices.setRestTemplate(vkTemplate)
        tokenServices.setTokenType("code")
        tokenServices.setAuthoritiesExtractor(authoritiesExtractor())
        tokenServices.setPrincipalExtractor(vkPrincipalExtractor())
        vkFilter.setTokenServices(tokenServices)
        return vkFilter
    }

    @Bean
    fun oauth2ClientFilterRegistration(filter: OAuth2ClientContextFilter): FilterRegistrationBean {
        val registration = FilterRegistrationBean()
        registration.filter = filter
        registration.order = -100
        return registration
    }

    @Bean
    @ConfigurationProperties("google.client")
    @ConditionalOnProperty(prefix = "google.client", name = [
        "clientId", "clientSecret", "accessTokenUri", "userAuthorizationUri",
        "clientAuthenticationScheme", "scope"
    ])
    fun googleClient(): AuthorizationCodeResourceDetails {
        return AuthorizationCodeResourceDetails()
    }

    @Bean
    @ConfigurationProperties("google.resource")
    @ConditionalOnProperty(prefix = "google.resource", name = ["userInfoUri"])
    fun googleResource(): ResourceServerProperties {
        return ResourceServerProperties()
    }

    @Bean
    @ConfigurationProperties("google")
    @ConditionalOnProperty(prefix = "google", name = ["loginUrl"])
    fun google(): GoogleProperties {
        return GoogleProperties()
    }


    @Bean
    @ConfigurationProperties("vk.client")
    @ConditionalOnProperty(prefix = "vk.client", name = [
        "clientId", "clientSecret", "accessTokenUri", "userAuthorizationUri",
        "authenticationScheme", "clientAuthenticationScheme", "scope"
    ])
    fun vkClient(): AuthorizationCodeResourceDetails {
        return AuthorizationCodeResourceDetails()
    }

    @Bean
    @ConfigurationProperties("vk.resource")
    @ConditionalOnProperty(prefix = "vk.resource", name = ["userInfoUri"])
    fun vkResource(): ResourceServerProperties {
        return ResourceServerProperties()
    }

    @Bean
    @ConfigurationProperties("vk")
    @ConditionalOnProperty(prefix = "vk", name = ["loginUrl"])
    fun vk(): VkProperties {
        return VkProperties()
    }

    @Bean
    fun tokenProvider(): AuthorizationCodeAccessTokenProvider {
        return VkAuthorizationCodeAccessTokenProvider()
    }

    @Bean
    fun authoritiesExtractor(): AuthoritiesExtractor {
        return AuthoritiesExtractorImpl(userStorage = userStorage)
    }

    @Bean
    fun googlePrincipalExtractor(): GooglePrincipalExtractor {
        return GooglePrincipalExtractor(userStorage = userStorage)
    }

    @Bean
    fun vkPrincipalExtractor(): VkPrincipalExtractor {
        return VkPrincipalExtractor(userStorage = userStorage)
    }
}