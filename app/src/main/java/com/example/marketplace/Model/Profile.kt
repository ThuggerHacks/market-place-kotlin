package com.example.marketplace.Model

import com.google.gson.annotations.SerializedName

class Profile {

    constructor(url:String = "",file:String = ""){
        this.url = url
        this.file = file
    }

    @SerializedName("url")
    var url:String
    @SerializedName("file")
    var file:String
}