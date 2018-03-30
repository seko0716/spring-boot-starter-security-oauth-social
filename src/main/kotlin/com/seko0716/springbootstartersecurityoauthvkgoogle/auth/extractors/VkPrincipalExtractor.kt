package com.seko0716.springbootstartersecurityoauthvkgoogle.auth.extractors

import com.seko0716.springbootstartersecurityoauthvkgoogle.domains.Role
import com.seko0716.springbootstartersecurityoauthvkgoogle.domains.User
import com.seko0716.springbootstartersecurityoauthvkgoogle.repository.UserStorage
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor

class VkPrincipalExtractor(var userRepository: UserStorage) : PrincipalExtractor {

    override fun extractPrincipal(map: MutableMap<String, Any>): Any {
        val result = OAuth2UserService.getDetails(map)
        val email = result.getOrDefault("email", "default_email")
        var user = userRepository.findOneByLogin(email)
        if (user == null) {
            user = User(login = email, roles = listOf(Role(name = "ROLE_DEFAULT")))
            return userRepository.save(user)
        }
        return user
    }
}