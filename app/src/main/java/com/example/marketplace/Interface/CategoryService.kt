package com.example.marketplace.Interface

import com.example.marketplace.Model.Category
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryService {
    @GET("category/{name}")
    fun getCategoryId(@Path("name") name:String): Call<Category>
    @GET("category")
    fun getCategoryAll(): Call<List<Category>>
}