package com.example.marketplace.Model

import com.google.gson.annotations.SerializedName

class Rating(
    @SerializedName("user_id")
    var userId:Int = 0,
    @SerializedName("rating_comments")
    var comment:String = "",
    @SerializedName("rater_id")
    var raterId:Int = 0,
    @SerializedName("rating_number")
    var ratingNumber:Int = 0,
    @SerializedName("id")
    var id:Int = 0,
    @SerializedName("error")
    var error:String = ""
) {
}