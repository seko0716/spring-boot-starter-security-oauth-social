package com.seko0716.springboot.starter.oauth2.social.repository

import com.seko0716.springboot.starter.oauth2.social.domains.User


open class UserStorage(private var userRepositoryDefault: UserRepositoryDefault) : IUserStorage {

    override fun save(entity: User): User {
        return userRepositoryDefault.save(entity)
    }

    override fun findOneBySocialAccountId(socialAccountId: String): User? {
        return userRepositoryDefault.findOneBySocialAccountId(socialAccountId)
    }

}