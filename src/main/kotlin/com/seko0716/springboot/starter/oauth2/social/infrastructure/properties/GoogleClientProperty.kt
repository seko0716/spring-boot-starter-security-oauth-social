package com.seko0716.springboot.starter.oauth2.social.infrastructure.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails

@ConfigurationProperties(prefix = "google.client")
class GoogleClientProperty : AuthorizationCodeResourceDetails()
