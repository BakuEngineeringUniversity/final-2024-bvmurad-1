package com.voluns5.voluns.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CustomConfig {
    @Value("\${user.validation.min-password-length}")
    private var minPasswordLength: Int = 6


    @Value("\${user.validation.email-regex}")
    var emailRegex: String = "^[A-Za-z0-9+_.-]+@(.+)$"

@Bean
fun userValidationConfig(): UserValidationConfig {
    return UserValidationConfig(

        minPasswordLength,
        emailRegex
    )
}
}