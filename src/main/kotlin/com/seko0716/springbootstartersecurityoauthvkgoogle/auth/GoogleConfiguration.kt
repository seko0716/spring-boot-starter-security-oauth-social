package com.seko0716.springbootstartersecurityoauthvkgoogle.auth

import com.seko0716.springbootstartersecurityoauthvkgoogle.auth.extractors.GooglePrincipalExtractor
import com.seko0716.springbootstartersecurityoauthvkgoogle.configurations.properties.GoogleProperties
import com.seko0716.springbootstartersecurityoauthvkgoogle.repository.IUserStorage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails

@Configuration
@ComponentScan
class GoogleConfiguration {
    @Autowired
    private lateinit var userStorage: IUserStorage

    @Autowired
    private lateinit var oauth2ClientContext: OAuth2ClientContext

    @Bean
    @ConditionalOnBean(name = ["googleClient", "googleResource", "authoritiesExtractor"])
    fun googleFilter(googleResource: ResourceServerProperties,
                     googleClient: AuthorizationCodeResourceDetails,
                     authoritiesExtractor: AuthoritiesExtractor): OAuth2ClientAuthenticationProcessingFilter {
        val googleFilter = OAuth2ClientAuthenticationProcessingFilter(google().loginUrl)
        val googleTemplate = OAuth2RestTemplate(googleClient, oauth2ClientContext)
        googleFilter.setRestTemplate(googleTemplate)
        val tokenServices = UserInfoTokenServices(googleResource.userInfoUri, googleClient.clientId)
        tokenServices.setRestTemplate(googleTemplate)
        tokenServices.setAuthoritiesExtractor(authoritiesExtractor)
        tokenServices.setPrincipalExtractor(googlePrincipalExtractor())
        googleFilter.setTokenServices(tokenServices)
        return googleFilter
    }

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
    fun google(): GoogleProperties {
        return GoogleProperties()
    }

    @Bean("googlePrincipalExtractor")
    fun googlePrincipalExtractor(): GooglePrincipalExtractor {
        return GooglePrincipalExtractor(userStorage = userStorage)
    }


}