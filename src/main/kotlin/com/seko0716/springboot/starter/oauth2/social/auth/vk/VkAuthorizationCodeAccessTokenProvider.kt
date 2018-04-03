package com.seko0716.springboot.starter.oauth2.social.auth.vk

import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider
import org.springframework.util.MultiValueMap

class VkAuthorizationCodeAccessTokenProvider : AuthorizationCodeAccessTokenProvider() {
    override fun getAccessTokenUri(resource: OAuth2ProtectedResourceDetails, form: MultiValueMap<String, String>): String {
        val accessTokenUri = resource.accessTokenUri

        if (logger.isDebugEnabled) {
            logger.debug("Retrieving token from $accessTokenUri")
        }

        var separator = "?"
        if (accessTokenUri.contains("?")) {
            separator = "&"
        }

        val builder = StringBuilder(accessTokenUri)
        for (key in form.keys) {
            builder.append(separator)
            builder.append(key).append("={").append(key).append("}")
            separator = "&"
        }

        return builder.toString()
    }
}