package com.seko0716.springboot.starter.oauth2.social.mongorepository

import com.seko0716.springboot.starter.oauth2.social.repository.IUserStorage
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean

@SpringBootConfiguration
@EnableAutoConfiguration
class MongoStopper {
    @Bean
    fun userStorage(userRepositoryMongo: UserRepositoryMongo): IUserStorage {
        return UserStorageMongo(userRepository = userRepositoryMongo)
    }
}

