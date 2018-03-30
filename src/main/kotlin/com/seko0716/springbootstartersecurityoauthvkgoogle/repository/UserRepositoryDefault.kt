package com.seko0716.springbootstartersecurityoauthvkgoogle.repository

import com.seko0716.springbootstartersecurityoauthvkgoogle.configurations.conditionals.MissingOtherImplementationCondition
import com.seko0716.springbootstartersecurityoauthvkgoogle.domains.User
import org.springframework.context.annotation.Conditional
import org.springframework.stereotype.Component

@Component
@Conditional(MissingOtherImplementationCondition::class)
class UserRepositoryDefault : UserRepository {

    override fun save(entity: User): User {
        return entity
    }

    override fun findOneByLogin(login: String): User? {
        return null
    }

}