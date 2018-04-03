package com.seko0716.springboot.starter.oauth2.social.mongorepository

import com.seko0716.springboot.starter.oauth2.social.domains.User
import com.seko0716.springboot.starter.oauth2.social.repository.IUserStorage

class UserStorageMongo constructor(var userRepository: UserRepositoryMongo) : IUserStorage {

    override fun save(entity: User): User {
        return userRepository.save(entity)
    }

    override fun findOneBySocialAccountId(socialAccountId: String): User? {
        return userRepository.findOneBySocialAccountId(socialAccountId)
    }
}