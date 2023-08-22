package com.example.marketplace.Model

import com.google.gson.annotations.SerializedName

class Product {

    constructor(
        id:Int = 0,
        userId:Int = 0,
        title:String = "",
        description:String = "",
        price:Double = 0.0,
        coverUrl:String = "",
        location:String = "",
        available:Int = 0,
        categoryId:Int = 0,
        date:String = "",
        error:String = ""
    ){
        this.id = id
        this.userId = userId
        this.price = price
        this.description = description
        this.title = title
        this.available = available
        this.coverUrl = coverUrl
        this.location = location
        this.categoryId = categoryId
        this.date = date
        this.error = error
    }


    @SerializedName("user_id")
    var userId:Int
    @SerializedName("id")
    var id:Int
    @SerializedName("title")
    var title:String
    @SerializedName("description")
    var description:String
    @SerializedName("price")
    var price:Double
    @SerializedName("cover_url")
    var coverUrl:String
    @SerializedName("location")
    var location:String
    @SerializedName("available")
    var available:Int
    @SerializedName("category_id")
    var categoryId:Int
    @SerializedName("date")
    var date:String
    @SerializedName("error")
    var error:String
}