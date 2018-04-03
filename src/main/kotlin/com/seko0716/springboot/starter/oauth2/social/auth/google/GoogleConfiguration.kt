package com.seko0716.springboot.starter.oauth2.social.auth.google

import com.seko0716.springboot.starter.oauth2.social.auth.extractors.GooglePrincipalExtractor
import com.seko0716.springboot.starter.oauth2.social.infrastructure.properties.GoogleClientProperty
import com.seko0716.springboot.starter.oauth2.social.infrastructure.properties.GoogleProperties
import com.seko0716.springboot.starter.oauth2.social.infrastructure.properties.GoogleResourceProperties
import com.seko0716.springboot.starter.oauth2.social.repository.IUserStorage
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
class GoogleConfiguration {
    @Autowired
    private lateinit var userStorage: IUserStorage
    @Autowired
    private lateinit var oauth2ClientContext: OAuth2ClientContext
    @Autowired
    private lateinit var authoritiesExtractor: AuthoritiesExtractor

    @Bean
    fun googleFilter(googleResource: GoogleResourceProperties,
                     googleClient: GoogleClientProperty): OAuth2ClientAuthenticationProcessingFilter {
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
        return GooglePrincipalExtractor(userStorage = userStorage, google = google())
    }


}