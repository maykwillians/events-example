package com.maykmenezes.desafiosicred.core

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://5f5a8f24d44d640016169133.mockapi.io/api/"

object ServiceConfig {

    private val okHttClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}