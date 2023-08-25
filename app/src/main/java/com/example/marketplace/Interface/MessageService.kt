package com.example.marketplace.Interface

import com.example.marketplace.Model.Message
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MessageService {
    @GET("message/chat/{chat_id}")
    fun messageByChat(@Path("chat_id") chatId:Int):Call<List<Message>>
    @GET("message/{id}")
    fun messageOne(@Path("id") id:Int):Call<Message>
    @POST("message")
    fun createMessage(@Body message:Message):Call<Message>
    @DELETE("message/{id}")
    fun deleteMessage(@Path("id") id:Int):Call<Message>
}