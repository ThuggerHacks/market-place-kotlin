package com.example.marketplace.Interface

import com.example.marketplace.Model.Rating
import retrofit2.Call
import retrofit2.http.*

interface RatingService {
    @GET("rating/{user_id}")
    fun getRatingByUser(@Path("user_id") id:Int):Call<List<Rating>>
    @POST("rating")
    fun addRating(@Body rating:Rating):Call<Rating>
    @DELETE("rating/{id}")
    fun deleteRating(@Path("id") id:Int):Call<Rating>
}