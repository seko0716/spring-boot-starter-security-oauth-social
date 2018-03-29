package com.seko0716.springbootstartersecurityoauthvkgoogle.domains

import org.bson.types.ObjectId


data class User(var id: ObjectId = ObjectId(),
                var login: String,
                var enabled: Boolean = true,
                var roles: List<Role> = ArrayList())