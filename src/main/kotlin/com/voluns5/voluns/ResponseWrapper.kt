package com.voluns5.voluns

data class ResponseWrapper<T>(
    val status: String,
    val message: String,
    val data: T? = null
)

