package com.seko0716.springboot.starter.oauth2.social.jparepository

import com.seko0716.springboot.starter.oauth2.social.domains.User
import org.bson.types.ObjectId
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepositoryJpa : CrudRepository<User, ObjectId> {
    fun findOneBySocialAccountId(socialAccountId: String): User?
}