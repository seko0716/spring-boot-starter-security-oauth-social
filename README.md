[![Build Status](https://travis-ci.org/Sergey34/spring-boot-starter-security-oauth-social.svg?branch=master)](https://travis-ci.org/Sergey34/spring-boot-starter-security-oauth-social)

# Spring Boot oAuth for social networks

[![Join the chat at https://gitter.im/spring-boot-starter-security-oauth-social/Lobby](https://badges.gitter.im/spring-boot-starter-security-oauth-social/Lobby.svg)](https://gitter.im/spring-boot-starter-security-oauth-social/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Основная цель проекта [oAuth-social](https://github.com/Sergey34/spring-boot-starter-security-oauth-social) упростить создание приложений, использующих авторизацию через социальные сети такие как Vk или Google.

## Статус ##

Пре-пре-альфа. 

Работает авторизация через Google и Vk. Конфигурация через замену бинов.


## Особенности ##

* Простая конфигурация в application.yml или application.properties для подключения нового сервера авторизации.

## Быстрый старт ##

Подключить плагин [JitPack](https://jitpack.io/) для получения jar с GitHub.

Maven:

```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
```

Gradle:

```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

Добавить зависимость в Maven:

```xml
<dependency>
  <groupId>com.github.Sergey34</groupId>
  <artifactId>spring-boot-starter-security-oauth-social</artifactId>
  <version>${version}.RELEASE</version>
</dependency>
```

Добавить зависимость в Gradle:

```gradle
compile('com.github.Sergey34:spring-boot-starter-security-oauth-social:0.0.1-RELEASE-SNAPSHOT')
```

//todo описать конфигурацию application.yml

После этого у вас есть сервис авторизации через Vk и Google. Для авторизации нужно перейти по ссылке **host**/connect/vk или **host**/connect/google.

Пользователь будут перенаправлен на страницу социальной сети для подтвержения доступа.

При дефолтных настройках пользователи не сохраняются в базу. Для добавления этого функцинонала необходимо перепределить класс _UserStorage_ и добавить его бин в контекст:

```kotlin
@Bean
fun userStorage(userRepositoryCrud: UserRepositoryJpa): UserStorage {
    return UserStorageJpa(userRepository = userRepositoryCrud)
}

class UserStorageJpa constructor(var userRepository: UserRepositoryJpa) : UserStorage() {
    override fun save(entity: User): User {
        return userRepository.save(entity)
    }

    override fun findOneByLogin(login: String): User? {
        return userRepository.findOneByLogin(login)
    }
}
```

//todo описать конфигурацию бинов для расширения



## Планы развития ##

* улучшить конфигурируемость стартера
* сделать версию для Spring Boot 2

