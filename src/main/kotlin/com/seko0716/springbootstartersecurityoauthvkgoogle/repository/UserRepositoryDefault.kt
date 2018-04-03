package com.seko0716.springbootstartersecurityoauthvkgoogle.repository

import com.seko0716.springbootstartersecurityoauthvkgoogle.domains.User
import org.bson.types.ObjectId
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepositoryDefault : CrudRepository<User, ObjectId> {
    fun findOneByLogin(login: String): User?
}