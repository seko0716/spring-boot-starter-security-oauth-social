package com.seko0716.springbootstartersecurityoauthvkgoogle.configurations.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "vk.client")
data class VkClientProperty(
        var clientId: String,
        var clientSecret: String,
        var accessTokenUri: String,
        var userAuthorizationUri: String,
        var authenticationScheme: String,
        var clientAuthenticationScheme: String
//        ,var scope: String
)
