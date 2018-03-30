package com.seko0716.springbootstartersecurityoauthvkgoogle.mongorepository

import com.seko0716.springbootstartersecurityoauthvkgoogle.repository.UserStorage
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean

@SpringBootConfiguration
@EnableAutoConfiguration
class MongoStopper {
    @Bean
    fun userStorage(userRepository: UserRepositoryMongo): UserStorage {
        return UserStorageMongo(userRepository = userRepository)
    }
}

