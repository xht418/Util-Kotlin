package com.example.utlikotlin

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitHelper {
    private val moshi = Moshi.Builder().run {
        add(KotlinJsonAdapterFactory())
        build()
    }

    private fun getRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder().run {
            addConverterFactory(MoshiConverterFactory.create(moshi))
            baseUrl(baseUrl)
            build()
        }
    }

    fun <T> create(baseUrl: String, apiInterface: Class<T>) = getRetrofit(baseUrl).create(apiInterface)
}