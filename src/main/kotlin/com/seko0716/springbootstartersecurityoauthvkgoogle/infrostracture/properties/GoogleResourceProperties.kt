package com.seko0716.springbootstartersecurityoauthvkgoogle.infrostracture.properties

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "google.resource")
class GoogleResourceProperties : ResourceServerProperties()