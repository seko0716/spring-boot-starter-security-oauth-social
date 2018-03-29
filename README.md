# Spring Boot oAuth for Vk and Google

Основная цель проекта [oAuth-vk-google](https://github.com/Sergey34/spring-boot-starter-security-oauth-vkgoogle) упростить создание приложений, использующих авторизацию через социальные сети такие как Vk или Google.
## Особенности ##

* ....

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
  <artifactId>spring-boot-starter-security-oauth-vkgoogle</artifactId>
  <version>${version}.RELEASE</version>
</dependency>
```

Добавить зависимость в Gradle:

```gradle
compile('com.github.Sergey34:spring-boot-starter-security-oauth-vkgoogle:0.0.1-RELEASE-SNAPSHOT')
```

//todo описать конфигурацию application.yml
//todo описать конфигурацию бинов для расширения

## Планы развития ##

* улучшить конфигурируемость стартера
* сделать версию для Spring Boot 2

