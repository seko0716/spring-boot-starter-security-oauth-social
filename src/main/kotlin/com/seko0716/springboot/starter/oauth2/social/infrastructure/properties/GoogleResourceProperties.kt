package com.seko0716.springboot.starter.oauth2.social.infrastructure.properties

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "google.resource")
class GoogleResourceProperties : ResourceServerProperties()