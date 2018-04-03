package com.seko0716.springboot.starter.oauth2.social.domains

import com.seko0716.springboot.starter.oauth2.social.infrostracture.annotations.NoArgs
import org.bson.types.ObjectId
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToMany

@NoArgs
@Entity
data class User(@Id var id: ObjectId = ObjectId(),
                var login: String,
                var enabled: Boolean = true,
                @ManyToMany var roles: List<Role> = ArrayList())