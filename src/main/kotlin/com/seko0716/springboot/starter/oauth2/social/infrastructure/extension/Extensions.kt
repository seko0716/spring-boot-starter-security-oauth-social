package com.seko0716.springboot.starter.oauth2.social.infrastructure.extension

inline fun <T> T?.createIfNull(creator: () -> T): T {
    return this ?: creator()
}