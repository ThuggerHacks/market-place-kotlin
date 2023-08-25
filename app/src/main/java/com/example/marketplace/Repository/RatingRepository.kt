package com.example.marketplace.Repository

import com.example.marketplace.Instance.RetrofitClient
import com.example.marketplace.Interface.RatingService
import com.example.marketplace.Model.Rating
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RatingRepository {

    fun getRatings(uid:Int,callback:(rating:List<Rating>) -> Unit){
        val list = ArrayList<Rating>()
        try{
            val service = RetrofitClient.createService(RatingService::class.java)
            val call = service.getRatingByUser(uid)

            call.enqueue( object : Callback<List<Rating>> {
                override fun onResponse(
                    call: Call<List<Rating>>,
                    response: Response<List<Rating>>
                ) {

                    if(response.isSuccessful){
                        callback(response.body()!!)
                    }else{
                        callback(list)
                    }
                }

                override fun onFailure(call: Call<List<Rating>>, t: Throwable) {
                    callback(list)
                }
            })
        }catch(e:Exception){
            callback(list)
        }
    }

    fun createRating(rating: Rating,callback:(rating:Rating) -> Unit){
        try{
            val service = RetrofitClient.createService(RatingService::class.java)
            val call = service.addRating(rating)
            call.enqueue( object : Callback<Rating> {
                override fun onResponse(
                    call: Call<Rating>,
                    response: Response<Rating>
                ) {

                    if(response.isSuccessful){
                        callback(response.body()!!)
                    }else{
                        callback(Rating())
                    }
                }

                override fun onFailure(call: Call<Rating>, t: Throwable) {
                    callback(Rating())
                }
            })
        }catch(e:Exception){
            callback(Rating())
        }
    }

    fun deleteRating(id: Int,callback:(rating:Rating) -> Unit){

        try{
            val service = RetrofitClient.createService(RatingService::class.java)
            val call = service.deleteRating(id)
            call.enqueue( object : Callback<Rating> {
                override fun onResponse(
                    call: Call<Rating>,
                    response: Response<Rating>
                ) {

                    if(response.isSuccessful){
                        callback(response.body()!!)
                    }else{
                        callback(Rating())
                    }
                }

                override fun onFailure(call: Call<Rating>, t: Throwable) {
                    callback(Rating())
                }
            })
        }catch(e:Exception){
            callback(Rating())
        }
    }
}