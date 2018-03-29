package com.seko0716.springbootstartersecurityoauthvkgoogle.repository

import org.bson.types.ObjectId
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import com.seko0716.springbootstartersecurityoauthvkgoogle.domains.User

@Repository
interface UserRepository : CrudRepository<User, ObjectId> {
    fun findOneByLogin(email: String): User?
}