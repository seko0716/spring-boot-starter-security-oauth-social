package com.seko0716.springboot.starter.oauth2.social.service


import com.seko0716.springboot.starter.oauth2.social.domains.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailImpl(private val user: User) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return user.roles
    }

    override fun getPassword(): String {
        return "pass"
    }

    override fun getUsername(): String {
        return user.login
    }

    override fun isAccountNonExpired(): Boolean {
        return user.enabled
    }

    override fun isAccountNonLocked(): Boolean {
        return user.enabled
    }

    override fun isCredentialsNonExpired(): Boolean {
        return user.enabled
    }

    override fun isEnabled(): Boolean {
        return user.enabled
    }
}