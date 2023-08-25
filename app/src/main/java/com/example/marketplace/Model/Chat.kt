package com.example.marketplace.Model

import com.google.gson.annotations.SerializedName

class Chat(
    @SerializedName("id")
    val id:Int = 0,
    @SerializedName("product_id")
    val productId:Int = 0,
    @SerializedName("sender_id")
    val senderId:Int = 0,
    @SerializedName("receiver_id")
    val receiverId:Int = 0,
    @SerializedName("date")
    val date:String = ""
) {
}