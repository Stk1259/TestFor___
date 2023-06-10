package com.example.testfor___.network

import com.example.testfor___.data.Categories
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


private const val BASE_URL = "https://run.mocky.io/"

val apiService: CategoriesApiService = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()
    .create(CategoriesApiService::class.java)

interface CategoriesApiService {
    @GET("v3/058729bd-1402-4578-88de-265481fd7d54")
    suspend fun getRepos(): Categories
}