package com.seko0716.springboot.starter.oauth2.social.domains

import com.seko0716.springboot.starter.oauth2.social.infrastructure.annotations.NoArgs
import org.bson.types.ObjectId
import org.springframework.security.core.GrantedAuthority
import javax.persistence.Entity
import javax.persistence.Id

@NoArgs
@Entity
data class Role(@Id var id: ObjectId = ObjectId(), var name: String) : GrantedAuthority {
    override fun getAuthority(): String {
        return name
    }
}