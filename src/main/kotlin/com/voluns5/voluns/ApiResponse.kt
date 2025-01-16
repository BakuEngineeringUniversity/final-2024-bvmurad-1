package com.voluns5.voluns

data class ApiResponse<T>(
    val status: String,
    val message: String,
    val data: T? = null
)
