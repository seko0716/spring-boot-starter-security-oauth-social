package com.seko0716.springboot.starter.oauth2.social.jparepository

import com.seko0716.springboot.starter.oauth2.social.repository.IUserStorage
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import javax.annotation.PostConstruct

@SpringBootConfiguration
@EnableAutoConfiguration
class JpaStopper {
    @PostConstruct
    fun init(): Unit {
        println("JpaStopper init")
    }

    @Bean
    fun userStorage(userRepositoryCrud: UserRepositoryJpa): IUserStorage {
        return UserStorageJpa(userRepository = userRepositoryCrud)
    }
}

