package com.seko0716.springbootstartersecurityoauthvkgoogle.mongorepository

import com.seko0716.springbootstartersecurityoauthvkgoogle.domains.User
import com.seko0716.springbootstartersecurityoauthvkgoogle.repository.UserStorage
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
    "com.seko0716.springbootstartersecurityoauthvkgoogle.repository",
    "com.seko0716.springbootstartersecurityoauthvkgoogle.mongorepository"])
@ComponentScan(basePackages = ["com.seko0716.springbootstartersecurityoauthvkgoogle"])
@EntityScan(basePackages = ["com.seko0716.springbootstartersecurityoauthvkgoogle"])
class MongoTestObjectId {

    @Autowired
    lateinit var userStorage: UserStorage

    @Test
    fun testObjectIdInJpa() {
        val user = userStorage.save(User(login = "123"))

        val foundedUser = userStorage.findOneByLogin("123")
        Assert.assertEquals(user, foundedUser)
    }
}