package com.voluns5.voluns.config

data class UserValidationConfig(

    val minUsernameLength: Int,
    val maxUsernameLength: Int,
    val minPasswordLength: Int,
    val emailRegex: String = "^[A-Za-z0-9_+&*-]+(?:\\.[A-Za-z0-9_+&*-]+)*@(?:[A-Za-z0-9-]+\\.)+[A-Za-z]{2,7}$"
)
