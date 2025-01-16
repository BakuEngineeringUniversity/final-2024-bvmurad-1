/*

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "user.validation")
class UserValidationConfig {
    var emailRegex: String = "^[A-Za-z0-9+_.-]+@(.+)$"
    var minPasswordLength: Int = 6
}
*/
package com.voluns5.voluns.config

data class UserValidationConfig(
    val minPasswordLength: Int,
    val emailRegex: String = "^[A-Za-z0-9+_.-]+@(.+)$"
)
