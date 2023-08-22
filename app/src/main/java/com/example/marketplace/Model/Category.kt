package com.example.marketplace.Model

import com.google.gson.annotations.SerializedName

class Category{
    constructor(id:Int = 0, category_name:String = ""){
        this.id = id
        this.category_name = category_name
    }

    @SerializedName("id")
    val id:Int
    @SerializedName("category_name")
    val category_name:String
    override fun toString(): String {
        return "$category_name"
    }


}