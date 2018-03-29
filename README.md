![oAuth-social](https://travis-ci.org/Sergey34/spring-boot-starter-security-oauth-social.svg?branch=master)

# Spring Boot oAuth for social networks

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

//todo описать конфигурацию бинов для расширения

Для авторизации перейти по ссылке /connect/vk или /connect/google

## Планы развития ##

* улучшить конфигурируемость стартера
* сделать версию для Spring Boot 2

