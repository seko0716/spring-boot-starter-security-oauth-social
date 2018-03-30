package com.seko0716.springbootstartersecurityoauthvkgoogle.configurations.conditionals

import com.seko0716.springbootstartersecurityoauthvkgoogle.repository.UserRepository
import org.springframework.context.annotation.Condition
import org.springframework.context.annotation.ConditionContext
import org.springframework.core.type.AnnotatedTypeMetadata
import org.springframework.core.type.classreading.AnnotationMetadataReadingVisitor

class MissingOtherImplementationCondition : Condition {

    override fun matches(context: ConditionContext, metadata: AnnotatedTypeMetadata): Boolean {
        if (context.beanFactory.getBeansOfType(UserRepository::class.java).isEmpty() && Hash.CASH.value || (metadata as AnnotationMetadataReadingVisitor).className == "com.seko0716.springbootstartersecurityoauthvkgoogle.repository.UserRepositoryCrud"){
            Hash.CASH.value = false
            return true
        }
        return false
    }
}

enum class Hash {
    CASH;
    var value: Boolean = true
}