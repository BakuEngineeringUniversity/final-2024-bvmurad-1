package com.voluns5.voluns.model

data class ResponseWrapper<T>(
    val status: String,
    val message: String,
    val data: T? = null
)

