package com.seko0716.springboot.starter.oauth2.social.service

import com.seko0716.springboot.starter.oauth2.social.repository.IUserStorage
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class UserDetailServiceImpl(private var userRepository: IUserStorage) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails? {
        val user = userRepository.findOneBySocialAccountId(username)
        return if (user != null) {
            UserDetailImpl(user)
        } else throw UsernameNotFoundException("Person Not Founded")
    }
}