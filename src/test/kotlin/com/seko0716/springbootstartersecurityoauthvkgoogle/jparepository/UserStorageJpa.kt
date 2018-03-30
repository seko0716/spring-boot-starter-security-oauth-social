package com.seko0716.springbootstartersecurityoauthvkgoogle.jparepository

import com.seko0716.springbootstartersecurityoauthvkgoogle.domains.User
import com.seko0716.springbootstartersecurityoauthvkgoogle.repository.UserStorage

class UserStorageJpa constructor(var userRepository: UserRepositoryJpa) : UserStorage() {

    override fun save(entity: User): User {
        return userRepository.save(entity)
    }

    override fun findOneByLogin(login: String): User? {
        return userRepository.findOneByLogin(login)
    }
}