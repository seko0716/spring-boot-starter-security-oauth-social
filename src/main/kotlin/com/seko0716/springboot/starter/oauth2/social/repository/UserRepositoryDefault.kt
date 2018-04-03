package com.seko0716.springboot.starter.oauth2.social.repository

import com.seko0716.springboot.starter.oauth2.social.domains.User
import org.bson.types.ObjectId
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepositoryDefault : CrudRepository<User, ObjectId> {
    fun findOneBySocialAccountId(socialAccountId: String): User?
}