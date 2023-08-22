package com.example.marketplace.Interface

import com.example.marketplace.Model.Profile
import com.example.marketplace.Model.User
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @POST("login")
    fun loginUser(@Body user:User):Call<User>
    @GET("user/{id}")
    fun getUser(@Path("id") id:Int):Call<User>
    @POST("user")
    fun createUser(@Body user:User):Call<User>
    @PUT("user/{id}")
    fun updateUser(@Path("id") id:Int, @Body user:User):Call<User>
}