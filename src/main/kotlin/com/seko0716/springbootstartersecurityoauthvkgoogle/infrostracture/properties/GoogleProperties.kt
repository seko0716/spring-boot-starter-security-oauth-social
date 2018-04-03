package com.seko0716.springbootstartersecurityoauthvkgoogle.infrostracture.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "google")
data class GoogleProperties(var loginUrl: String = "/connect/google") {
}