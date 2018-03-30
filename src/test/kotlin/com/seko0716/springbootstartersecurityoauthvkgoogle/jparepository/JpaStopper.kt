package com.seko0716.springbootstartersecurityoauthvkgoogle.jparepository

import com.seko0716.springbootstartersecurityoauthvkgoogle.repository.UserStorage
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean

@SpringBootConfiguration
@EnableAutoConfiguration
class JpaStopper {
    @Bean
    fun userStorage(userRepositoryCrud: UserRepositoryJpa): UserStorage {
        return UserStorageJpa(userRepository = userRepositoryCrud)
    }
}

