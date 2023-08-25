package com.example.marketplace.Instance


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object{
        private lateinit var INSTANCE: Retrofit

        fun getInstance():Retrofit{
            val baseUrl = "https://7de2-197-235-240-75.ngrok-free.app/api/"
            val httpClient = OkHttpClient.Builder()
            if(!::INSTANCE.isInitialized){
                synchronized(RetrofitClient::class.java){
                    INSTANCE = Retrofit.Builder()
                        .client(httpClient.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(baseUrl)
                        .build()
                }
            }

            return INSTANCE
        }

        fun <S> createService(service: Class<S>):S{
            return getInstance().create(service)
        }
    }
}