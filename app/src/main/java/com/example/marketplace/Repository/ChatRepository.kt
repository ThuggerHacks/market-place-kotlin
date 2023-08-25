package com.example.marketplace.Repository

import com.example.marketplace.Instance.RetrofitClient
import com.example.marketplace.Interface.ChatService
import com.example.marketplace.Model.Chat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatRepository {

    fun getUsersChat(pid:Int,rid:Int,sid:Int, callback:(chat:Chat) -> Unit){
        try {
            val service = RetrofitClient.createService(ChatService::class.java)
            val call = service.getMerchantChats(pid,rid,sid)

            call.enqueue( object : Callback<Chat> {
                override fun onResponse(call: Call<Chat>, response: Response<Chat>) {
                    if(response.isSuccessful){
                        callback(response.body()!!)
                    }else{
                        callback(Chat())
                    }
                }

                override fun onFailure(call: Call<Chat>, t: Throwable) {
                    callback(Chat())
                }
            })
        }catch (e:Exception){
            callback(Chat())
        }
    }

    fun getChats(pid:Int, callback:(chat:List<Chat>) -> Unit){
        val list = ArrayList<Chat>()
        try {
            val service = RetrofitClient.createService(ChatService::class.java)
            val call = service.getChatProdId(pid)
            call.enqueue( object : Callback<List<Chat>> {
                override fun onResponse(call: Call<List<Chat>>, response: Response<List<Chat>>) {
                    if(response.isSuccessful){
                        callback(response.body()!!)
                    }else{
                        callback(list)
                    }
                }

                override fun onFailure(call: Call<List<Chat>>, t: Throwable) {
                    callback(list)
                }
            })
        }catch (e:Exception){
            callback(list)
        }
    }

    fun getChatsByUserId(uid:Int, callback:(chat:List<Chat>) -> Unit){
        val list = ArrayList<Chat>()
        try {
            val service = RetrofitClient.createService(ChatService::class.java)
            val call = service.getChatUser(uid)
            call.enqueue( object : Callback<List<Chat>> {
                override fun onResponse(call: Call<List<Chat>>, response: Response<List<Chat>>) {
                    if(response.isSuccessful){
                        callback(response.body()!!)
                    }else{
                        callback(list)
                    }
                }

                override fun onFailure(call: Call<List<Chat>>, t: Throwable) {
                    callback(list)
                }
            })
        }catch (e:Exception){
            callback(list)
        }
    }



    fun createChat(chat:Chat,callback:(chat:Chat) -> Unit){
        try {
            val service = RetrofitClient.createService(ChatService::class.java)
            val call = service.createChat(chat)
            call.enqueue( object : Callback<Chat> {
                override fun onResponse(call: Call<Chat>, response: Response<Chat>) {
                    if(response.isSuccessful){
                        callback(response.body()!!)
                    }else{
                        callback(Chat())
                    }
                }

                override fun onFailure(call: Call<Chat>, t: Throwable) {
                    callback(Chat())
                }
            })
        }catch (e:Exception){
            callback(Chat())
        }
    }

    fun deleteChat(id:Int,callback:(chat:Chat) -> Unit){
        try {
            val service = RetrofitClient.createService(ChatService::class.java)
            val call = service.deleteChat(id)
            call.enqueue( object : Callback<Chat> {
                override fun onResponse(call: Call<Chat>, response: Response<Chat>) {
                    if(response.isSuccessful){
                        callback(response.body()!!)
                    }else{
                        callback(Chat())
                    }
                }

                override fun onFailure(call: Call<Chat>, t: Throwable) {
                    callback(Chat())
                }
            })
        }catch (e:Exception){
            callback(Chat())
        }
    }


    fun getOne(id:Int,callback:(chat:Chat) -> Unit){
        try {
            val service = RetrofitClient.createService(ChatService::class.java)
            val call = service.getChatOne(id)
            call.enqueue( object : Callback<Chat> {
                override fun onResponse(call: Call<Chat>, response: Response<Chat>) {
                    if(response.isSuccessful){
                        callback(response.body()!!)
                    }else{
                        callback(Chat())
                    }
                }

                override fun onFailure(call: Call<Chat>, t: Throwable) {
                    callback(Chat())
                }
            })
        }catch (e:Exception){
            callback(Chat())
        }
    }

}