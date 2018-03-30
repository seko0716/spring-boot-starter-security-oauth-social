package com.seko0716.springbootstartersecurityoauthvkgoogle.configurations.conditionals

import com.seko0716.springbootstartersecurityoauthvkgoogle.repository.UserRepository
import org.springframework.beans.factory.BeanCreationException
import org.springframework.context.annotation.Condition
import org.springframework.context.annotation.ConditionContext
import org.springframework.core.type.AnnotatedTypeMetadata
import org.springframework.core.type.classreading.AnnotationMetadataReadingVisitor

class MissingOtherImplementationCondition : Condition {

    override fun matches(context: ConditionContext, metadata: AnnotatedTypeMetadata): Boolean {
        val forName = Class.forName((metadata as AnnotationMetadataReadingVisitor).className)
        val beansOfType: MutableMap<String, UserRepository>?
        try {
            beansOfType = context.beanFactory.getBeansOfType(UserRepository::class.java)
        }catch (e : BeanCreationException){
            return false
        }
        if (beansOfType.isEmpty() && Hash.CASH.value ||
                forName.interfaces.contains(UserRepository::class.java) && forName.isInterface){
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