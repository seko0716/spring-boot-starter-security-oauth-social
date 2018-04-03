package com.seko0716.springboot.starter.oauth2.social.infrastructure.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "google")
data class GoogleProperties(var loginUrl: String = "/connect/google", var defaultRoles: List<String> = listOf("DEFAULT_ROLE")) {
}