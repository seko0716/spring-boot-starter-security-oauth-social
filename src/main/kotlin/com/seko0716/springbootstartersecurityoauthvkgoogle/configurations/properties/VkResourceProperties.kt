package com.seko0716.springbootstartersecurityoauthvkgoogle.configurations.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "vk.resource")
data class VkResourceProperties(var userInfoUri: String = "")