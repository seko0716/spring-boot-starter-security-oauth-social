package com.seko0716.springbootstartersecurityoauthvkgoogle.repository

import com.seko0716.springbootstartersecurityoauthvkgoogle.domains.User
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Conditional
import org.springframework.stereotype.Service

class UserRepositoryDefault : UserRepository {

    override fun save(entity: User): User {
        return entity
    }

    override fun findOneByLogin(login: String): User? {
        return null
    }

}