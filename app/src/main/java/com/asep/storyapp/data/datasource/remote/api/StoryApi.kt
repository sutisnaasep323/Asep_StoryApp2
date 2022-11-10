package com.asep.storyapp.data.datasource.remote.api


import com.asep.storyapp.data.datasource.remote.api.StoryApi.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val client = OkHttpClient.Builder()
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(client)
    .build()

object StoryApi {
    const val BASE_URL = "https://story-api.dicoding.dev/v1/"
    val retrofitService: StoryService by lazy {
        retrofit.create(StoryService::class.java)
    }
}