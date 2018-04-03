package com.seko0716.springboot.starter.oauth2.social.auth.extractors

import com.seko0716.springboot.starter.oauth2.social.repository.IUserStorage
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor
import org.springframework.security.core.GrantedAuthority

class AuthoritiesExtractorImpl(var userStorage: IUserStorage) : AuthoritiesExtractor {

    override fun extractAuthorities(map: MutableMap<String, Any>): List<GrantedAuthority> {
        val email = OAuth2UserService.getEmail(OAuth2UserService.getDetails(map))
        val user = userStorage.findOneByLogin(email)
        if (user != null) {
            return user.roles.toMutableList()
        }
        return ArrayList()
    }

}