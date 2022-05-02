package com.example.utlikotlin

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import retrofit2.Retrofit

object RetrofitHelper {
    private val contentType = "application/json".toMediaType()
    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    private fun getRetrofit(baseUrl: String) = Retrofit.Builder().run {
        addConverterFactory(json.asConverterFactory(contentType))
        baseUrl(baseUrl)
        build()
    }

    fun <T> create(baseUrl: String, apiInterface: Class<T>) = getRetrofit(baseUrl).create(apiInterface)
}