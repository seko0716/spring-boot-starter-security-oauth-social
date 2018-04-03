package com.seko0716.springboot.starter.oauth2.social.mongorepository

import com.seko0716.springboot.starter.oauth2.social.domains.User
import com.seko0716.springboot.starter.oauth2.social.repository.IUserStorage
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DataMongoTest
@EnableMongoRepositories(basePackages = [
    "com.seko0716.springboot.starter.oauth2.social.repository",
    "com.seko0716.springboot.starter.oauth2.social.mongorepository"
])
@ComponentScan(basePackages = ["com.seko0716.springboot.starter.oauth2.social"])
@EntityScan(basePackages = ["com.seko0716.springboot.starter.oauth2.social"])
class MongoTestObjectId {

    @Autowired
    lateinit var userStorage: IUserStorage

    @Test
    fun testObjectIdInJpa() {
        val user = userStorage.save(User(login = "123", socialAccountId = "234"))

        val foundedUser = userStorage.findOneBySocialAccountId("234")
        Assert.assertEquals(user, foundedUser)
    }
}