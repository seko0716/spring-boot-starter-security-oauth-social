# Spring Boot oAuth for social networks

[![Build Status](https://travis-ci.org/Sergey34/spring-boot-starter-security-oauth-social.svg?branch=master)](https://travis-ci.org/Sergey34/spring-boot-starter-security-oauth-social)
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

При дефолтных настройках пользователи сохраняются в базу такими какими были созданы в PrincipalExtracto. Для добавления какого-то функцинонала необходимо перепределить класс _UserStorage_ и добавить его бин в контекст:

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


## Расширение ##

Чтобы добавить новый сервис авторизации достаточно определить бин **OAuth2ClientAuthenticationProcessingFilter**

Например бин для авторизации через Google:

```kotlin
@Bean
fun googleFilter(): OAuth2ClientAuthenticationProcessingFilter {
  val googleFilter = OAuth2ClientAuthenticationProcessingFilter(google().loginUrl)
  val googleTemplate = OAuth2RestTemplate(googleClient(), oauth2ClientContext)
  googleFilter.setRestTemplate(googleTemplate)
  val tokenServices = UserInfoTokenServices(googleResource().userInfoUri, googleClient().clientId)
  tokenServices.setRestTemplate(googleTemplate)
  tokenServices.setAuthoritiesExtractor(authoritiesExtractor())
  tokenServices.setPrincipalExtractor(googlePrincipalExtractor())
  googleFilter.setTokenServices(tokenServices)
  return googleFilter
}
```

**GooglePrincipalExtractor** класс конвертирующий отверт сервера в доменную сущность **User**, ее также лучше переопределить:

```kotlin
@Bean
fun googlePrincipalExtractor(userStorage: IUserStorage): PrincipalExtractor {
    return GooglePrincipalExtractor(userStorage = userStorage)
}

class GooglePrincipalExtractor(var userStorage: IUserStorage) : PrincipalExtractor {

    override fun extractPrincipal(map: MutableMap<String, Any>): Any {
        map["_authServiceType"] = "GOOGLE"
        val result = OAuth2UserService.getDetails(map)
        val email = result["email"]
        var user = userStorage.findOneByLogin(email!!)
        if (user == null) {
            user = User(login = email, roles = listOf(Role(name = "ROLE_DEFAULT")))
            return userStorage.save(user)
        }
        return user
    }
}
```

Сущностью **User** используется только для авторизации, все дополнительные параметры пользователя в Вашем прокте нужно вынести в отдельную сущность, одним из полей которой будет **User**. Это позволит избежать сложностей при наследовании и использовани реляционныз БД.

Наследование от **User** запрещено, класс помечен как финальный.

## Планы развития ##

* сделать версию для Spring Boot 2

