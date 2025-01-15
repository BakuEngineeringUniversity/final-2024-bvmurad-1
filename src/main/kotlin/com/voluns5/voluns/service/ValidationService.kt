package com.voluns5.voluns.service

import com.voluns5.voluns.config.UserValidationConfig
import org.springframework.stereotype.Service

@Service
class ValidationService(val config: UserValidationConfig) {

    fun validatePassword(password: String): Boolean {
        return password.length >= config.minPasswordLength
    }

    fun validateEmail(email: String): Boolean {
        return Regex(config.emailRegex).matches(email)
    }
}
