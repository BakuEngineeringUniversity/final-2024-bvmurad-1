package com.voluns5.voluns.response

data class ApiResponse<T>(
    val status: String,
    val message: String,
    val data: T? = null
)
