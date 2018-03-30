package com.seko0716.springbootstartersecurityoauthvkgoogle.mongorepository

import com.seko0716.springbootstartersecurityoauthvkgoogle.domains.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepositoryMongo : MongoRepository<User, ObjectId> {
    fun findOneByLogin(login: String): User?
}