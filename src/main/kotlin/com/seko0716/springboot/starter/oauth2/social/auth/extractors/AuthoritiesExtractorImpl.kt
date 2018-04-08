package com.seko0716.springboot.starter.oauth2.social.auth.extractors

import com.seko0716.springboot.starter.oauth2.social.repository.IUserStorage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor
import org.springframework.security.core.GrantedAuthority

class AuthoritiesExtractorImpl(var userStorage: IUserStorage,
                               var OAuth2UserService: OAuth2UserService,
                               var idField: String) : AuthoritiesExtractor {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    override fun extractAuthorities(map: MutableMap<String, Any>): List<GrantedAuthority> {
        log.debug("credential map: {}", map)
        val socialAccountId = OAuth2UserService.getDetails(map)[idField]
        val user = userStorage.findOneBySocialAccountId(socialAccountId!!)
        if (user != null) {
            return user.roles.toMutableList()
        }
        log.debug("user with social account id {} not found", socialAccountId)
        return ArrayList()
    }

}