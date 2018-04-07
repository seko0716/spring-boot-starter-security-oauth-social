package com.seko0716.springboot.starter.oauth2.social.auth.extractors

import com.seko0716.springboot.starter.oauth2.social.domains.Role
import com.seko0716.springboot.starter.oauth2.social.domains.User
import com.seko0716.springboot.starter.oauth2.social.infrastructure.properties.VkProperties
import com.seko0716.springboot.starter.oauth2.social.repository.IUserStorage
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor

class VkPrincipalExtractor(var userStorage: IUserStorage, var vk: VkProperties, var OAuth2UserService: OAuth2UserService) : PrincipalExtractor {
    private val authServiceType = "VK"

    override fun extractPrincipal(map: MutableMap<String, Any>): Any {
        map["_authServiceType"] = authServiceType
        val result = OAuth2UserService.getDetails(map)
        val socialAccountId = result[vk.idField]
        var user = userStorage.findOneBySocialAccountId(socialAccountId!!)
        if (user == null) {
            user = User(login = result[vk.loginField]!!,
                    socialAccountId = socialAccountId,
                    email = result[vk.emailField],
                    firstName = result[vk.firstNameField],
                    lastName = result[vk.lastNameField],
                    roles = vk.defaultRoles.map { Role(name = it) },
                    authServiceType = authServiceType)
            return userStorage.save(user)
        }
        return user
    }
}