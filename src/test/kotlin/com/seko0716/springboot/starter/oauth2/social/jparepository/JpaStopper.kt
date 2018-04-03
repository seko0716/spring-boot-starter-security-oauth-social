package com.seko0716.springboot.starter.oauth2.social.jparepository

import com.seko0716.springboot.starter.oauth2.social.repository.IUserStorage
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean

@SpringBootConfiguration
@EnableAutoConfiguration
class JpaStopper {
    @Bean
    fun userStorage(userRepositoryCrud: UserRepositoryJpa): IUserStorage {
        return UserStorageJpa(userRepository = userRepositoryCrud)
    }
}

