package com.seko0716.springboot.starter.oauth2.social.jparepository

import com.seko0716.springboot.starter.oauth2.social.domains.User
import com.seko0716.springboot.starter.oauth2.social.repository.IUserStorage
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DataJpaTest
@EnableJpaRepositories(basePackages = [
    "com.seko0716.springboot.starter.oauth2.social.jparepository",
    "com.seko0716.springboot.starter.oauth2.social.repository"
])
@ComponentScan(basePackages = ["com.seko0716.springboot.starter.oauth2.social"])
@EntityScan(basePackages = ["com.seko0716.springboot.starter.oauth2.social"])
class JpaTestObjectId {

    @Autowired
    lateinit var userStorage: IUserStorage

    @Test
    fun testObjectIdInJpa() {
        val user = userStorage.save(User(login = "123", socialAccountId = "34"))

        val foundedUser = userStorage.findOneBySocialAccountId("34")
        Assert.assertEquals(user, foundedUser)
    }
}