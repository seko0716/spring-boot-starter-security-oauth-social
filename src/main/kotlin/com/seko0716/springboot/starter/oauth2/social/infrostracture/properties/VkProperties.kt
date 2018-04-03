package com.seko0716.springboot.starter.oauth2.social.infrostracture.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "vk")
data class VkProperties(var loginUrl: String = "/connect/vk") {
}