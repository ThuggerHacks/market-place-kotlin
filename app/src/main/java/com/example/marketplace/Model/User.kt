package com.example.marketplace.Model

import com.google.gson.annotations.SerializedName

class User{

    constructor(id:Int = 0,name:String = "",email:String = "", password:String = "",phone:String = "",location:String = "",profile:String = "", error:String = ""){
        this.id = id
        this.name = name
        this.email = email
        this.password = password
        this.phone = phone
        this.location = location
        this.profilePic = profile
        this.error = error
    }

    @SerializedName("id")
    var id:Int
    @SerializedName("name")
    var name:String
    @SerializedName("email")
    var email:String
    @SerializedName("password")
    var password:String
    @SerializedName("phone")
    var phone:String
    @SerializedName("location")
    var location:String
    @SerializedName("profile")
    var profilePic:String
    @SerializedName("error")
    var error:String
}