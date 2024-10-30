package com.marktkachenko.lebenindeutschland.services

import com.marktkachenko.lebenindeutschland.models.TranslationResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface DeepLApiService {
    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("v2/translate")
    suspend fun translateText(
        @Field("auth_key") apiKey: String,
        @Field("text") text: String,
        @Field("target_lang") targetLanguage: String
    ): TranslationResponse
}