package com.seko0716.springboot.starter.oauth2.social.auth.extractors

import com.seko0716.springboot.starter.oauth2.social.domains.Role
import com.seko0716.springboot.starter.oauth2.social.domains.User
import com.seko0716.springboot.starter.oauth2.social.infrastructure.extension.ifNull
import com.seko0716.springboot.starter.oauth2.social.infrastructure.properties.GoogleProperties
import com.seko0716.springboot.starter.oauth2.social.repository.IUserStorage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor

class GooglePrincipalExtractor(var userStorage: IUserStorage, var google: GoogleProperties, var OAuth2UserService: OAuth2UserService) : PrincipalExtractor {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)
    private val authServiceType = "GOOGLE"

    override fun extractPrincipal(map: MutableMap<String, Any>): Any {
        log.trace("credential map: {}", map)
        map["_authServiceType"] = authServiceType
        val result = OAuth2UserService.getDetails(map)
        val socialAccountId = result[google.idField]
        val user = userStorage
                .findOneBySocialAccountId(socialAccountId!!)
                .ifNull {
                    log.debug("user with social account id {} not found", socialAccountId)
                    var userT = User(login = result[google.loginField]!!,
                            socialAccountId = socialAccountId,
                            email = result[google.emailField],
                            firstName = result[google.firstNameField],
                            lastName = result[google.lastNameField],
                            roles = google.defaultRoles.map { Role(name = it) },
                            authServiceType = authServiceType)
                    userT = userStorage.save(userT)
                    log.debug("user be created {}", userT)
                    return userT
                }
        log.trace("user with social account id {} exist {}", socialAccountId, user)
        return user
    }
}