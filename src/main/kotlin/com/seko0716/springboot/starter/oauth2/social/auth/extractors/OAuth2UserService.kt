package com.seko0716.springboot.starter.oauth2.social.auth.extractors

import java.util.*

class OAuth2UserService {

    companion object {
        fun getDetails(map: Map<String, Any>): Map<String, String> {
            val result = HashMap<String, String>()
            val mapResult = map["response"]
            if (mapResult != null && mapResult is List<*>) {
                mapResult.forEach { it ->
                    if (it is Map<*, *>) {
                        it.forEach { key, value -> result[key.toString()] = value.toString() }
                    }
                }
                result["email"] = getEmail(result)
                return result
            }
            return map.map { entry -> entry.key to entry.value.toString() }.toMap()
        }

        fun getEmail(result: Map<String, String>): String {
            val authServiceType = result.getOrDefault("_authServiceType", "ERROR")
            return when (authServiceType) {
                "GOOGLE" -> result["email"]!!
                "VK" -> calculateEmailForVk(result)
                "ERROR" -> throw IllegalStateException("can not find _authServiceType")
                else -> {
                    throw IllegalStateException("can not find _authServiceType")
                }
            }

        }

        private fun calculateEmailForVk(result: Map<String, String>): String {
            val firstName = result["first_name"]
            val lastName = result["last_name"]
            if (firstName.isNullOrBlank() || lastName.isNullOrBlank()) {
                throw IllegalStateException("first_name and last_name is null or blank")
            }
            return result.getOrDefault("email", "$lastName.$firstName@vk_mail.com")
        }
    }
}