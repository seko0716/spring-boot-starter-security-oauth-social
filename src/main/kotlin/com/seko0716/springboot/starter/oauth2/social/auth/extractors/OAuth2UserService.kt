package com.seko0716.springboot.starter.oauth2.social.auth.extractors

import java.util.*


class OAuth2UserService {
    fun getDetails(map: Map<String, Any>): Map<String, String> {
        val authServiceType = map.getOrDefault("_authServiceType", "ERROR")

        return when (authServiceType) {
            "GOOGLE" -> map.map { entry -> entry.key to entry.value.toString() }.toMap()
            "VK" -> vkConvert(map)
            "ERROR" -> throw IllegalStateException("can not find _authServiceType")
            else -> {
                throw IllegalStateException("can not find _authServiceType")
            }
        }
    }


    private fun vkConvert(map: Map<String, Any>): Map<String, String> {
        val result = HashMap<String, String>()
        val mapResult = map["response"]
        if (mapResult is List<*>) {
            mapResult.forEach { it ->
                if (it is Map<*, *>) {
                    it.forEach { key, value -> result[key.toString()] = value.toString() }
                }
            }
            return result
        }
        throw  throw IllegalStateException("can not find key 'response'")
    }

}