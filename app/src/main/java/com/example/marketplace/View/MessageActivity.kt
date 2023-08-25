package com.example.marketplace.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.Adapter.Listeners.OnMessageClickListener
import com.example.marketplace.Adapter.MessageListAdapter
import com.example.marketplace.Model.Message
import com.example.marketplace.Model.Product
import com.example.marketplace.R
import com.example.marketplace.Repository.ChatRepository
import com.example.marketplace.Repository.MessageRepository
import com.example.marketplace.Repository.ProductRepository
import com.example.marketplace.Repository.UserRepository
import com.example.marketplace.databinding.ActivityMessageBinding

class MessageActivity : AppCompatActivity(), OnMessageClickListener {
    private lateinit var binding: ActivityMessageBinding
    private lateinit var adapter:MessageListAdapter
    private lateinit var list:ArrayList<Message>
    private var receiverId:Int = 0
    private var userId:Int = 0
    private var chatId:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val chat= intent.getIntExtra("chatId",0)
        chatId = chat
        val storage = this.getSharedPreferences("user", MODE_PRIVATE)
        val user = storage.getInt("id",0)
        userId = user
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //toolbar config
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.custom_toolbar)
        toolbar.setNavigationOnClickListener {

        }
        val toolbarLabel = findViewById<TextView>(R.id.toolbar_label)
        toolbarLabel.setText("Braimo Selimane")
        toolbarLabel.setOnClickListener {
            val intent = Intent(this,VendorActivity::class.java)
            intent.putExtra("receiverId",receiverId)
            startActivity(intent)
        }
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        loadMessages(binding.messageRecyclerView,chatId)

        //logout
        val logoutBtn = findViewById<ImageView>(R.id.logout_pic_toolbar)
        logoutBtn.setOnClickListener{
            logout()
        }

        //send message
        binding.sendBtn.setOnClickListener {
            //simple animation
            binding.sendBtn.alpha = 0.5f
            binding.sendBtn.setText("ENVIANDO...")
            Handler(Looper.getMainLooper()).postDelayed({
                binding.sendBtn.alpha = 1.0f
            },100)

            val message = binding.messageInputToSend.text.toString()
            val dataMessage = Message(message,chatId,userId,receiverId)
            MessageRepository().createMessage(dataMessage){
                if(it?.id != 0){
                    binding.messageInputToSend.setText("")
                    binding.sendBtn.setText("ENVIAR")
                    MessageRepository().getAllByChat(chatId){ c ->
                        adapter.updateList(c)
                    }
                }else{
                    binding.sendBtn.setText("ENVIAR")
                    Toast.makeText(this,"Houve um erro ao enviar a mensagem!",Toast.LENGTH_SHORT).show()
                }
            }

        }

        //buy btn
        binding.btnBuy.setOnClickListener{
            val productRepository = ProductRepository()
            val product = Product(0,userId,"","",0.0,"","",2,0,"")
           ChatRepository().getOne(chatId){
               productRepository.getOneProduct(it.productId){p ->
                   if(p.userId != userId){
                     if(p.available == 1){
                         productRepository.updateProduct(p.id,product){ u ->
                             if(u?.id != 0){
                                 binding.btnBuy.setText("Vendido")
                                 Toast.makeText(this,"Aguarde pela confirmaco do vendedor!",Toast.LENGTH_SHORT).show()
                             }else{
                                 Toast.makeText(this,"Houve um erro!",Toast.LENGTH_SHORT).show()
                             }
                         }
                     }else{
                         Toast.makeText(this,"Aguarde pela confirmaco do vendedor!",Toast.LENGTH_SHORT).show()
                     }
                   }else{
                       if(p.available == 2){
                           val product = Product(0,userId,"","",0.0,"","",3,0,"")
                           productRepository.updateProduct(p.id,product){ u ->
                               if(u?.id != 0){
                                   binding.btnBuy.setText("Vendido")
                                   Toast.makeText(this,"Produto vendido com sucesso!",Toast.LENGTH_SHORT).show()
                               }else{
                                   Toast.makeText(this,"Houve um erro!",Toast.LENGTH_SHORT).show()
                               }
                           }
                       }
                   }
               }
           }
        }


    }



    override fun onClick(message: Message) {
        MessageRepository().deleteMessage(message.id){
            Toast.makeText(this,"Mensagem Apagada com sucesso!",Toast.LENGTH_SHORT).show()
            MessageRepository().getAllByChat(chatId){ m ->
                adapter.updateList(m)
            }
        }
    }

    private fun loadMessages(recyclerView:RecyclerView, chatId:Int){

        recyclerView.layoutManager = LinearLayoutManager(this)
       MessageRepository().getAllByChat(chatId){ list ->
           adapter = MessageListAdapter(list,this, userId,this)
           recyclerView.adapter = adapter
       }

        ChatRepository().getOne(chatId){
            ProductRepository().getOneProduct(it.productId){ p ->
                binding.productTitleMessage.text = p.title.toString()
                binding.productDateMessage.text = p.date.toString()

                if(p.available == 0){
                    binding.btnBuy.text = "Vendido"
                    binding.btnBuy.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
                    binding.btnBuy.setTextColor(ContextCompat.getColor(this, R.color.black))
                }else if(userId == p.userId && p.available == 1){
                    binding.btnBuy.text = "Vendendo"
                    binding.btnBuy.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
                    binding.btnBuy.setTextColor(ContextCompat.getColor(this, R.color.black))
                }else if(userId == p.userId && p.available == 2){
                    binding.btnBuy.text = "Confirmar Compra"
                    binding.btnBuy.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
                    binding.btnBuy.setTextColor(ContextCompat.getColor(this, R.color.black))
                }else{
                    binding.btnBuy.text = "Comprar"
                }
            }

            if(it.receiverId == userId){
                UserRepository().getUserOne(it.senderId){ u ->
                    val toolbarTitle = findViewById<TextView>(R.id.toolbar_label)
                    toolbarTitle.text = u.name.toString()
                    receiverId = u.id
                }
            }else{
                UserRepository().getUserOne(it.receiverId){ u ->
                    val toolbarTitle = findViewById<TextView>(R.id.toolbar_label)
                    toolbarTitle.text = u.name.toString()
                    receiverId = u.id
                }
            }
        }

    }

    fun logout(){
        val storage = this.getSharedPreferences("user", MODE_PRIVATE)
        val editor = storage.edit()
        editor.remove("id")
        editor.apply()
        startActivity(Intent(this,LoginActivity::class.java))
    }

}