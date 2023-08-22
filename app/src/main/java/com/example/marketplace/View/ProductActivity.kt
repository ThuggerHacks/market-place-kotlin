package com.example.marketplace.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.example.marketplace.Model.Product
import com.example.marketplace.Model.User
import com.example.marketplace.R
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

        loadProduct(paramId){
            if(it.available == 1){
                available.setText("Disponivel")
            }else{
                available.setText("Indisponivel")
            }
            Picasso.get().load(it.coverUrl).into(cover)
            title.setText(it.title)
            location.setText(it.location)
            data.setText(it.date)
            desc.setText(it.description)

            //restrict user from commenting at his own post
            val storage = this.getSharedPreferences("user", MODE_PRIVATE)
            val userId = storage.getInt("id",0)

            if(userId == it.userId){
                binding.formMessageContainer.visibility = View.GONE
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

    fun logout(){
        val storage = this.getSharedPreferences("user", MODE_PRIVATE)
        val editor = storage.edit()
        editor.remove("id")
        editor.apply()
        startActivity(Intent(this,LoginActivity::class.java))
    }
}