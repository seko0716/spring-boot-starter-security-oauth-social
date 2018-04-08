package com.seko0716.springboot.starter.oauth2.social.auth.google

import com.seko0716.springboot.starter.oauth2.social.auth.extractors.AuthoritiesExtractorImpl
import com.seko0716.springboot.starter.oauth2.social.auth.extractors.GooglePrincipalExtractor
import com.seko0716.springboot.starter.oauth2.social.auth.extractors.OAuth2UserService
import com.seko0716.springboot.starter.oauth2.social.infrastructure.properties.GoogleClientProperty
import com.seko0716.springboot.starter.oauth2.social.infrastructure.properties.GoogleProperties
import com.seko0716.springboot.starter.oauth2.social.infrastructure.properties.GoogleResourceProperties
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

@Configuration
@ComponentScan(basePackages = ["com.seko0716.springboot.starter.oauth2.social"])
@EnableConfigurationProperties(value = [
    GoogleClientProperty::class,
    GoogleProperties::class,
    GoogleResourceProperties::class
])
@ConditionalOnProperty(prefix = "google", name = [
    "client.clientId", "client.clientSecret", "client.accessTokenUri", "client.userAuthorizationUri",
    "client.clientAuthenticationScheme", "client.scope", "resource.userInfoUri"
])
class GoogleConfiguration @Autowired constructor(var userStorage: IUserStorage,
                                                 var oauth2ClientContext: OAuth2ClientContext,
                                                 var oAuth2UserService: OAuth2UserService) {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Bean
    fun googleFilter(googleResource: GoogleResourceProperties,
                     googleClient: GoogleClientProperty): OAuth2ClientAuthenticationProcessingFilter {
        val googleFilter = OAuth2ClientAuthenticationProcessingFilter(google().loginUrl)
        val googleTemplate = OAuth2RestTemplate(googleClient, oauth2ClientContext)
        googleFilter.setRestTemplate(googleTemplate)
        val tokenServices = UserInfoTokenServices(googleResource.userInfoUri, googleClient.clientId)
        tokenServices.setRestTemplate(googleTemplate)
        tokenServices.setAuthoritiesExtractor(googleAuthoritiesExtractor())
        tokenServices.setPrincipalExtractor(googlePrincipalExtractor())
        googleFilter.setTokenServices(tokenServices)
        log.trace("init google oauth2 filter")
        return googleFilter
    }

    @Bean("googleClient")
    @ConfigurationProperties("google.client")
    fun googleClient(): GoogleClientProperty {
        return GoogleClientProperty()
    }

    @Bean("googleResource")
    @ConfigurationProperties("google.resource")
    fun googleResource(): GoogleResourceProperties {
        return GoogleResourceProperties()
    }

    @Bean("google")
    @ConfigurationProperties("google")
    fun google(): GoogleProperties {
        return GoogleProperties()
    }

    @Bean("googlePrincipalExtractor")
    fun googlePrincipalExtractor(): GooglePrincipalExtractor {
        return GooglePrincipalExtractor(userStorage = userStorage, google = google(), OAuth2UserService = oAuth2UserService)
    }

    @Bean
    fun googleAuthoritiesExtractor(): AuthoritiesExtractor {
        return AuthoritiesExtractorImpl(userStorage = userStorage, OAuth2UserService = oAuth2UserService, idField = google().idField)
    }


}