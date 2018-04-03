package com.seko0716.springboot.starter.oauth2.social.jparepository

import com.seko0716.springboot.starter.oauth2.social.domains.User
import com.seko0716.springboot.starter.oauth2.social.repository.IUserStorage

class UserStorageJpa constructor(var userRepository: UserRepositoryJpa) : IUserStorage {

    override fun save(entity: User): User {
        return userRepository.save(entity)
    }

    override fun findOneByLogin(login: String): User? {
        return userRepository.findOneByLogin(login)
    }
}