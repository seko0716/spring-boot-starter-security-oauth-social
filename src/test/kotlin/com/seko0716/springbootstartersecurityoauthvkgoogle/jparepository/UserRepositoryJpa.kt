package com.seko0716.springbootstartersecurityoauthvkgoogle.jparepository

import com.seko0716.springbootstartersecurityoauthvkgoogle.domains.User
import org.bson.types.ObjectId
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepositoryJpa : CrudRepository<User, ObjectId> {
    fun findOneByLogin(login: String): User?
}