package com.seko0716.springbootstartersecurityoauthvkgoogle.repository

import com.seko0716.springbootstartersecurityoauthvkgoogle.domains.User


open class UserStorage(private var userRepositoryDefault: UserRepositoryDefault) : IUserStorage {

    override fun save(entity: User): User {
        return userRepositoryDefault.save(entity)
    }

    override fun findOneByLogin(login: String): User? {
        return userRepositoryDefault.findOneByLogin(login)
    }

}