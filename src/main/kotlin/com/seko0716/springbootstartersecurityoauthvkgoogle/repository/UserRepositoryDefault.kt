package com.seko0716.springbootstartersecurityoauthvkgoogle.repository

import com.seko0716.springbootstartersecurityoauthvkgoogle.domains.User
import org.springframework.stereotype.Service

@Service
class UserRepositoryDefault : UserRepository {

    override fun save(entity: User): User {
        return entity
    }

    override fun findOneByLogin(login: String): User? {
        return null
    }

}