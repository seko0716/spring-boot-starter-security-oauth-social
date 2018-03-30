package com.seko0716.springbootstartersecurityoauthvkgoogle.mongorepository

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import javax.annotation.PostConstruct

@SpringBootConfiguration
@EnableAutoConfiguration
class Stopper {
    @PostConstruct
    fun init(): Unit {
        println("MongoRepositoryStopper init")
    }
}

