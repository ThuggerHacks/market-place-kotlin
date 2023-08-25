package com.example.marketplace.Interface

import com.example.marketplace.Model.Chat
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatService {
    @GET("chat/{product_id}/{receiver_id}/{sender_id}")
    fun getMerchantChats(@Path("product_id") productId:Int,@Path("receiver_id") receiverId:Int, @Path("sender_id") senderId:Int ):Call<Chat>
    @GET("chat/list/{product_id}")
    fun getChatProdId(@Path("product_id") productId: Int):Call<List<Chat>>
    @GET("chat/user/{id}")
    fun getChatUser(@Path("id") id:Int):Call<List<Chat>>
    @GET("chat/{id}")
    fun getChatOne(@Path("id") id:Int):Call<Chat>
    @POST("chat")
    fun createChat(@Body chat:Chat):Call<Chat>
    @DELETE("chat/{id}")
    fun deleteChat(@Path("id") id:Int):Call<Chat>

}