package com.seko0716.springboot.starter.oauth2.social.infrastructure.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "vk")
data class VkProperties(var loginUrl: String = "/connect/vk",
                        var defaultRoles: List<String> = listOf("DEFAULT_ROLE"),
                        var loginField: String = "first_name",
                        var emailField: String = "email",
                        var idField: String = "id",
                        val firstNameField: String = "first_name",
                        val lastNameField: String = "last_name")