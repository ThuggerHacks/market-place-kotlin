package com.example.marketplace.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.example.marketplace.Model.Chat
import com.example.marketplace.Model.Message
import com.example.marketplace.Model.Product
import com.example.marketplace.Model.User
import com.example.marketplace.R
import com.example.marketplace.Repository.ChatRepository
import com.example.marketplace.Repository.MessageRepository
import com.example.marketplace.Repository.ProductRepository
import com.example.marketplace.Repository.UserRepository
import com.example.marketplace.databinding.ActivityProductBinding
import com.squareup.picasso.Picasso

class ProductActivity : AppCompatActivity() {
    private lateinit var binding:ActivityProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //custom toolbar configs
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.custom_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val paramId = intent.getStringExtra("productId").toString().toInt()
        val available = binding.productAvailability
        val cover = binding.productImageCover
        val title = binding.productTitle
        val location = binding.productLocation
        val data = binding.productDate
        val vendor = binding.vendoerName
        val desc = binding.productDesc
        val price = binding.productPrice
        val btnSend = binding.btnSend
        val storage = this.getSharedPreferences("user", MODE_PRIVATE)
        val userId = storage.getInt("id",0)

        loadProduct(paramId){
            if(it.available != 0){
                available.setText("Disponivel")
            }else{
                available.setText("Indisponivel")
            }
            Picasso.get().load(it.coverUrl).into(cover)
            title.setText(it.title)
            location.setText(it.location)
            data.setText(it.date)
            desc.setText(it.description)
            price.setText("${it.price.toString()} MT")
            //restrict user from commenting at his own post

            if(userId == it.userId){
                binding.formMessageContainer.visibility = View.GONE
            }else{
                binding.formMessageContainer.visibility = View.VISIBLE
            }

            getVendor(it.userId){
                vendor.setText(it.name)
            }
        }

        //logout
        val logoutBtn = findViewById<ImageView>(R.id.logout_pic_toolbar)
        logoutBtn.setOnClickListener{
            logout()
        }

        //sendMessage
        btnSend.setOnClickListener {
            btnSend.setText("ENVIANDO...")
            //little animation
            btnSend.alpha = 0.5f
            Handler(Looper.getMainLooper()).postDelayed({
                btnSend.alpha = 1.0f
            },100)

            val message = binding.messageInput.text.toString()
            loadProduct(paramId){
                getVendor(it.userId){ user ->
                    val chat = Chat(0,it.id,userId,user.id)
                    createChat(chat,message,""){ c ->
                        if(c?.id != 0){
                            btnSend.setText("ENVIAR")
                            Toast.makeText(this,"Mensagem Enviada com sucesso!",Toast.LENGTH_SHORT).show()
                            val intent = Intent(this,MessageActivity::class.java)
                            intent.putExtra("chatId",c.id)
                            startActivity(intent)
                        }else{
                            btnSend.setText("ENVIAR")
                            Toast.makeText(this,"Houve um erro ao enviar a mensagem!",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        }


    }


    private fun checkForChat(pid:Int,rid:Int, sid:Int, callback:(chat:Chat) -> Unit){
        val chat = ChatRepository()
        chat.getUsersChat(pid,rid,sid){
            if(it?.id != 0){
                callback(it)
            }else{
                callback(Chat())
            }
        }
    }

    private fun createChat(chat:Chat,message:String,photo:String,callback: (chat: Chat) -> Unit){
        checkForChat(chat.productId,chat.receiverId,chat.senderId){
            if(it.id != 0){
                val dataMessage = Message(message,it.id,chat.senderId,it.receiverId,"",photo,0)
                //chat exists
                sendMessage(dataMessage){ m ->
                    if(m?.id != 0){
                       callback(it)
                    }else{
                      callback(Chat())
                    }
                }
            }else{
                //chat doesn't exist
                val chatRepository = ChatRepository()
                chatRepository.createChat(chat){ c ->
                    if(c?.id != 0) {
                        val dataMessage = Message(message,c.id,c.senderId,c.receiverId,"",photo,0)
                        //chat created
                        sendMessage(dataMessage){ m ->
                            if(m?.id != 0){
                                //message sent
                               callback(c)
                            }else{
                                //error sending message
                               callback(Chat())
                            }
                        }
                    }else{
                        //chat not created
                        callback(Chat())
                    }
                }
            }
        }
    }

    private fun sendMessage(message:Message, callback:(message:Message) -> Unit){
        val messageRepository = MessageRepository()
        messageRepository.createMessage(message){ m ->
            if(m?.id != 0){
                callback(m)
            }else{
                callback(Message())
            }
        }
    }

    private fun loadProduct(id:Int,callback:(Product) -> Unit){
       val service = ProductRepository()
       service.getOneProduct(id){
           callback(it)
       }
    }

    private fun getVendor(id:Int,callback: (User) -> Unit){
        val service = UserRepository()
        service.getUserOne(id){
            callback(it)
        }
    }

    private fun logout(){
        val storage = this.getSharedPreferences("user", MODE_PRIVATE)
        val editor = storage.edit()
        editor.remove("id")
        editor.apply()
        startActivity(Intent(this,LoginActivity::class.java))
    }
}