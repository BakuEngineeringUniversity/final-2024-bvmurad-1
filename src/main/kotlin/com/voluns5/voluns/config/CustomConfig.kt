package com.voluns5.voluns.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CustomConfig {

    @Value("\${user.validation.min-username-length}")
    private var minUsernameLength: Int = 5

    @Value("\${user.validation.max-username-length}")
    private var maxUsernameLength: Int = 18

    @Value("\${user.validation.min-password-length}")
    private var minPasswordLength: Int = 6

    @Value("\${user.validation.email-regex}")
    var emailRegex: String = "^[A-Za-z0-9_+&*-]+(?:\\.[A-Za-z0-9_+&*-]+)*@(?:[A-Za-z0-9-]+\\.)+[A-Za-z]{2,7}$"

    @Bean
fun userValidationConfig(): UserValidationConfig {
    return UserValidationConfig(

        minUsernameLength,
        maxUsernameLength,
        minPasswordLength,
        emailRegex
    )
}
}