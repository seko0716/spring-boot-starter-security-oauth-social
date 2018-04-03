package com.seko0716.springbootstartersecurityoauthvkgoogle.infrostracture.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "vk")
data class VkProperties(var loginUrl: String = "/connect/vk") {
}