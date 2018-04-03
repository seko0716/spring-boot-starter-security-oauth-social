package com.seko0716.springboot.starter.oauth2.social

import com.seko0716.springboot.starter.oauth2.social.security.Oauth2WebSecurityConfigurerAdapter
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@RunWith(SpringRunner::class)
class TestContext {

    @Autowired
    lateinit var oAuth2WebSecurityConfigurerAdapter: Oauth2WebSecurityConfigurerAdapter

    @Test
    fun testContext() {
        println(oAuth2WebSecurityConfigurerAdapter)
        println("done")
        Assert.assertEquals(2, oAuth2WebSecurityConfigurerAdapter.oAuth2ClientAuthenticationProcessingFilters.size)
    }
}