package com.seko0716.springbootstartersecurityoauthvkgoogle.configurations.conditionals

import com.seko0716.springbootstartersecurityoauthvkgoogle.repository.UserRepository
import org.springframework.context.annotation.Condition
import org.springframework.context.annotation.ConditionContext
import org.springframework.core.type.AnnotatedTypeMetadata

class MissingOtherImplementationCondition : Condition {
    override fun matches(context: ConditionContext, metadata: AnnotatedTypeMetadata): Boolean {
        return context.beanFactory.getBeansOfType(UserRepository::class.java).isEmpty()
    }
}