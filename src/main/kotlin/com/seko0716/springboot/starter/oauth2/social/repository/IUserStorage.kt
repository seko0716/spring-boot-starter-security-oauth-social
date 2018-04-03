package com.seko0716.springboot.starter.oauth2.social.repository

import com.seko0716.springboot.starter.oauth2.social.domains.User

interface IUserStorage {
    fun findOneBySocialAccountId(socialAccountId: String): User?

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity
     * @return the saved entity
     */
    fun save(entity: User): User
}