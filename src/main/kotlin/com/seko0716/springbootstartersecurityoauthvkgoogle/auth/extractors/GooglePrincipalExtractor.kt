package com.seko0716.springbootstartersecurityoauthvkgoogle.auth.extractors

import com.seko0716.springbootstartersecurityoauthvkgoogle.domains.Role
import com.seko0716.springbootstartersecurityoauthvkgoogle.domains.User
import com.seko0716.springbootstartersecurityoauthvkgoogle.repository.UserRepository
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor

//@Component
class GooglePrincipalExtractor(var userRepository: UserRepository) : PrincipalExtractor {

    override fun extractPrincipal(map: MutableMap<String, Any>): Any {
        val result = OAuth2UserService.getDetails(map)
        val email = result["email"]
        var user = userRepository.findOneByLogin(email!!)
        if (user == null) {
            user = User(login = email, roles = listOf(Role(name = "ROLE_DEFAULT")))
            return userRepository.save(user)
        }
        return user
    }
}