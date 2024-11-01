package com.marktkachenko.lebenindeutschland.models.deepLTranslate

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://api-free.deepl.com/"

    val api: DeepLApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DeepLApiService::class.java)
    }
}