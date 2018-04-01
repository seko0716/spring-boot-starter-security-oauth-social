# Spring Boot oAuth for social networks

[![Build Status](https://travis-ci.org/Sergey34/spring-boot-starter-security-oauth-social.svg?branch=master)](https://travis-ci.org/Sergey34/spring-boot-starter-security-oauth-social)
[![Join the chat at https://gitter.im/spring-boot-starter-security-oauth-social/Lobby](https://badges.gitter.im/spring-boot-starter-security-oauth-social/Lobby.svg)](https://gitter.im/spring-boot-starter-security-oauth-social/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Основная цель проекта [oAuth-social](https://github.com/Sergey34/spring-boot-starter-security-oauth-social) упростить создание приложений, использующих авторизацию через социальные сети такие как Vk или Google.

## Статус ##

betta.

В master ветке самая актуальная, но возмножно нестабильная версия. Все релизы находятся в отдельных ветках.


## Особенности ##

* Авторизация через VK и Google из коробки.
* Простая конфигурация в application.yml или application.properties для подключения нового сервера авторизации.
* Для добавления нового сервиса oAuth авторизации достаточно определить всего 2 бина.
* Возможна работа как с реляционной базой данных так и с NoSql. Стартер никак не привязан ни к одной из баз данных.

## Быстрый старт ##

Подключить плагин [JitPack](https://jitpack.io/) для получения jar с GitHub.

Выбрать конкретную ветку или коммит и получить конфиг для зависимости можно [тут](https://jitpack.io/#Sergey34/spring-boot-starter-security-oauth-social).

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
В файл application.yml или application.properties необходимо указать некоторые данные для авторизации. Эти данные Вы получите при регистрации приложения.

[Приложения VK](https://vk.com/apps?act=manage)

Тип риложения: Standalone-приложение, в настройках выставить **Open API: Включён**, указать в поле **Базовый домен** на котором будет развернуто Ваше приложение.

[Приложения Google](https://console.developers.google.com/apis/credentials)

Необходим тип "Идентификатор клиентов OAuth 2.0". После создания приложения в его настройках нужно указать "разрешенные источники JavaScript" и "разрешенные URI перенаправления".

```yml
google:
  client:
    clientId: clientId.apps.googleusercontent.com
    clientSecret: clientSecret
    accessTokenUri: https://accounts.google.com/o/oauth2/token
    userAuthorizationUri: https://accounts.google.com/o/oauth2/v2/auth
    clientAuthenticationScheme: form
    scope: profile email
  resource:
    userInfoUri:  https://www.googleapis.com/oauth2/v3/userinfo

vk:
  client:
    clientId: clientId
    clientSecret: clientSecret
    accessTokenUri: https://oauth.vk.com/access_token
    userAuthorizationUri: https://oauth.vk.com/authorize
    authenticationScheme: query
    clientAuthenticationScheme: form
  resource:
    userInfoUri:  https://api.vk.com/method/users.get?v=5.52
```

После этого у вас есть сервис авторизации через Vk и Google. Для авторизации нужно перейти по ссылке **host**/connect/vk или **host**/connect/google.

Пользователь будут перенаправлен на страницу социальной сети для подтвержения доступа.

## Расширение ##

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

Чтобы добавить новый сервис авторизации достаточно определить бин **OAuth2ClientAuthenticationProcessingFilter**.

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
* сделать демо 
* добавить проверку наличия необходимых пропертей в application файле. если провертей нет валить приложение с корректной ошибке

