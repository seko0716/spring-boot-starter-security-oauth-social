package com.seko0716.springbootstartersecurityoauthvkgoogle.configurations.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails

@ConfigurationProperties(prefix = "vk.client")
class VkClientProperty : AuthorizationCodeResourceDetails()