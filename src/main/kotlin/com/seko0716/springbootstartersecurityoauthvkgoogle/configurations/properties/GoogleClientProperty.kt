package com.seko0716.springbootstartersecurityoauthvkgoogle.configurations.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "google.client")
data class GoogleClientProperty(
        var clientId: String,
        var clientSecret: String,
        var accessTokenUri: String,
        var userAuthorizationUri: String,
        var clientAuthenticationScheme: String,
        var scope: String
)
