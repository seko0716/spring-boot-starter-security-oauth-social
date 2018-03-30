package com.seko0716.springbootstartersecurityoauthvkgoogle.mongorepository

import com.seko0716.springbootstartersecurityoauthvkgoogle.domains.User
import com.seko0716.springbootstartersecurityoauthvkgoogle.repository.UserRepository
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
class TestObjectId {

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun testObjectIdInJpa() {
        val user = userRepository.save(User(login = "123"))

        val foundedUser = userRepository.findOneByLogin("123")
        Assert.assertEquals(user, foundedUser)
    }
}