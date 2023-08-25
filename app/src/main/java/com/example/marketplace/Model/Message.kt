package com.example.marketplace.Model

import com.google.gson.annotations.SerializedName

class Message(
    @SerializedName("message")
    val message: String = "",
    @SerializedName("chat_id")
    val chatId:Int = 0,
    @SerializedName("sender_id")
    val senderId:Int = 0,
    @SerializedName("receiver_id")
    val receiverId:Int = 0,
    @SerializedName("date")
    val date:String = "",
    @SerializedName("photo_attach")
    val photoAttach:String = "",
    @SerializedName("id")
    val id:Int = 0
) {
}