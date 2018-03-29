package com.seko0716.springbootstartersecurityoauthvkgoogle.domains

import org.bson.types.ObjectId
import org.springframework.security.core.GrantedAuthority


data class Role(var id: ObjectId = ObjectId(), var name: String) : GrantedAuthority {
    override fun getAuthority(): String {
        return name
    }
}