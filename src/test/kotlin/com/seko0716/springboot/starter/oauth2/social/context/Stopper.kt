package com.seko0716.springboot.starter.oauth2.social.context

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import javax.annotation.PostConstruct

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = ["com.seko0716.springboot.starter.oauth2.social.context", "com.seko0716.springboot.starter.oauth2.social"])
@EntityScan(basePackages = ["com.seko0716.springboot.starter.oauth2.social.context", "com.seko0716.springboot.starter.oauth2.social.domains"])
class Stopper {
    @PostConstruct
    fun init(): Unit {
        println("ContextStopper init")
    }
}