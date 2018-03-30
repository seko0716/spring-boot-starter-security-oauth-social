package com.seko0716.springbootstartersecurityoauthvkgoogle.repository

import com.seko0716.springbootstartersecurityoauthvkgoogle.domains.User
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
@EnableJpaRepositories(basePackages = ["com.seko0716.springbootstartersecurityoauthvkgoogle.repository"])
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