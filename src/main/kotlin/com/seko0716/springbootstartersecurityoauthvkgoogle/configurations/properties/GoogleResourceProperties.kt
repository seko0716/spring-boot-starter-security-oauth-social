package com.seko0716.springbootstartersecurityoauthvkgoogle.configurations.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "google.resource")
data class GoogleResourceProperties(var userInfoUri: String)