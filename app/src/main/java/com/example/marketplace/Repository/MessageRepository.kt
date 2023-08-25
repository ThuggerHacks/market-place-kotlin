package com.example.marketplace.Repository

import com.example.marketplace.Instance.RetrofitClient
import com.example.marketplace.Interface.MessageService
import com.example.marketplace.Model.Message
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessageRepository {

    fun getAllByChat(id:Int, callback:(message:List<Message>) -> Unit){
        val list = ArrayList<Message>()
        try{
            val service = RetrofitClient.createService(MessageService::class.java)
            val call = service.messageByChat(id)

            call.enqueue(object :  Callback<List<Message>> {

                override fun onResponse(
                    call: Call<List<Message>>,
                    response: Response<List<Message>>
                ) {
                    if(response.isSuccessful){
                        callback(response.body()!!)
                    }else{
                        callback(list)
                    }
                }

                override fun onFailure(call: Call<List<Message>>, t: Throwable) {
                    callback(list)
                }
            })

        }catch(e:Exception){
            callback(list)
        }
    }

    fun getOne(id:Int, callback:(message: Message) -> Unit){
        val list = ArrayList<Message>()
        try{
            val service = RetrofitClient.createService(MessageService::class.java)
            val call = service.messageOne(id)

            call.enqueue(object :  Callback<Message> {

                override fun onResponse(
                    call: Call<Message>,
                    response: Response<Message>
                ) {
                    if(response.isSuccessful){
                        callback(response.body()!!)
                    }else{
                        callback(Message())
                    }
                }

                override fun onFailure(call: Call<Message>, t: Throwable) {
                    callback(Message())
                }
            })

        }catch(e:Exception){
            callback(Message())
        }
    }

    fun createMessage(message:Message, callback:(message: Message) -> Unit){
        try{
            val service = RetrofitClient.createService(MessageService::class.java)
            val call = service.createMessage(message)

            call.enqueue(object :  Callback<Message> {

                override fun onResponse(
                    call: Call<Message>,
                    response: Response<Message>
                ) {
                    if(response.isSuccessful){
                        callback(response.body()!!)
                    }else{
                        callback(Message())
                    }
                }

                override fun onFailure(call: Call<Message>, t: Throwable) {
                    callback(Message())
                }
            })

        }catch(e:Exception){
            callback(Message())
        }
    }

    fun deleteMessage(id:Int, callback:(message: Message) -> Unit){
        val list = ArrayList<Message>()
        try{
            val service = RetrofitClient.createService(MessageService::class.java)
            val call = service.deleteMessage(id)

            call.enqueue(object :  Callback<Message> {

                override fun onResponse(
                    call: Call<Message>,
                    response: Response<Message>
                ) {
                    if(response.isSuccessful){
                        callback(response.body()!!)
                    }else{
                        callback(Message())
                    }
                }

                override fun onFailure(call: Call<Message>, t: Throwable) {
                    callback(Message())
                }
            })

        }catch(e:Exception){
            callback(Message())
        }
    }
}