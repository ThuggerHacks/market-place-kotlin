package com.example.marketplace.Interface

import com.example.marketplace.Model.Product
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductService {

    @POST("product")
    fun addProduct(@Body product:Product): Call<Product>

    @GET("product/{id}")
    fun getOne(@Path("id") id:Int): Call<Product>

    @GET("product")
    fun getAll(): Call<List<Product>>

    @DELETE("product/{id}")
    fun deleteOne(@Path("id") id:Int): Call<Product>

    @PUT("product/{id}")
    fun updateOne(@Path("id") id:Int, @Body product:Product): Call<Product>

    @GET("product/user/{id}")
    fun getByUserId(@Path("id") userId:Int):Call<List<Product>>

}