package com.example.marketplace.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.Adapter.MessageListAdapter
import com.example.marketplace.Model.Message
import com.example.marketplace.R
import com.example.marketplace.databinding.ActivityMessageBinding

class MessageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessageBinding
    private lateinit var adapter:MessageListAdapter
    private lateinit var list:ArrayList<Message>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            startActivity(intent)
        }
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        loadMessages(binding.messageRecyclerView)

        //logout
        val logoutBtn = findViewById<ImageView>(R.id.logout_pic_toolbar)
        logoutBtn.setOnClickListener{
            logout()
        }
    }

    private fun loadMessages(recyclerView:RecyclerView){
        list = ArrayList()
        list.add(Message("Hello"))
        list.add(Message("Hi"))
        list.add(Message("Good"))
        list.add(Message("fine finefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefine" +
                "finefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefine" +
                "finefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefine" +
                "finefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefine" +
                "finefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefinefine "))
        adapter = MessageListAdapter(list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }

    fun logout(){
        val storage = this.getSharedPreferences("user", MODE_PRIVATE)
        val editor = storage.edit()
        editor.remove("id")
        editor.apply()
        startActivity(Intent(this,LoginActivity::class.java))
    }

}