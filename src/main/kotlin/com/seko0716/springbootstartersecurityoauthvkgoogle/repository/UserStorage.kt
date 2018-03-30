package com.seko0716.springbootstartersecurityoauthvkgoogle.repository

import com.seko0716.springbootstartersecurityoauthvkgoogle.domains.User

open class UserStorage : UserRepository {

    override fun save(entity: User): User {
        return entity
    }

    override fun findOneByLogin(login: String): User? {
        return null
    }

}