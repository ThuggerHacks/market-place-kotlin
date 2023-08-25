package com.example.marketplace.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.Adapter.Listeners.OnRatingClickListener
import com.example.marketplace.Adapter.RatingListAdapter
import com.example.marketplace.Model.Rating
import com.example.marketplace.Model.User
import com.example.marketplace.R
import com.example.marketplace.Repository.RatingRepository
import com.example.marketplace.Repository.UserRepository
import com.example.marketplace.databinding.ActivityVendorBinding
import com.squareup.picasso.Picasso

class VendorActivity : AppCompatActivity(), OnRatingClickListener {
    private lateinit var binding: ActivityVendorBinding
    private lateinit var adapter: RatingListAdapter
    private lateinit var list:ArrayList<Rating>
    private var selectedItem:Int = 0
    private  var vendorId:Int = 0
    private var userId:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVendorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vendorId = intent.getIntExtra("receiverId",0)

        val storage = this.getSharedPreferences("user", MODE_PRIVATE)
        userId = storage.getInt("id",0)
        //toolbar configs
        val toolbarLabel = findViewById<TextView>(R.id.toolbar_label)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.custom_toolbar)
        toolbarLabel.setText("Carregando...")
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //spinner config
        val spinner = binding.spinnerRating
        val spinnerData = arrayOf("1","2","3","4","5")
        spinner.adapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,spinnerData)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
               selectedItem = spinnerData[position].toString().toInt()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        //load ratings
        loadRatings(binding.ratingContainerRecyclerView)

        //logout
        val logoutBtn = findViewById<ImageView>(R.id.logout_pic_toolbar)
        logoutBtn.setOnClickListener{
            logout()
        }

        getVendorData()

        //rate send
        val ratingRepository =  RatingRepository()
        binding.btnRate.setOnClickListener {
            val message = binding.ratingMessage.text.toString()
            val rating = Rating(vendorId,message,userId,selectedItem)

            if(message.trim() == ""){
                Toast.makeText(this,"Por favor Escreva um comentario!",Toast.LENGTH_SHORT).show()
            }else{
               ratingRepository.createRating(rating){
                   if(it?.error == null || it?.id != 0){
                       binding.ratingMessage.setText("")
                       Toast.makeText(this,"Envido com sucesso",Toast.LENGTH_SHORT).show()
                       ratingRepository.getRatings(vendorId){ list ->
                           adapter.updateRating(list)
                       }
                   }else{
                       Toast.makeText(this,it.error,Toast.LENGTH_SHORT).show()
                   }
               }
            }

        }


    }

    override fun onClick(rating: Rating) {
        if(rating.raterId == userId) {
            RatingRepository().deleteRating(rating.id) {
                if(it?.error != null){
                    Toast.makeText(this,"Apagado com sucesso",Toast.LENGTH_SHORT).show()
                   RatingRepository().getRatings(vendorId){
                       adapter.updateRating(it)
                   }
                }else{
                    Toast.makeText(this,rating.error,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getVendorData(){
        UserRepository().getUserOne(vendorId){
            binding.vendorEmail.setText(it.email)
            binding.vendorLocation.setText(it.location)
            binding.vendorPhone.setText(it.phone.toString())
            val toolbar = findViewById<TextView>(R.id.toolbar_label)
            toolbar.setText(it.name)
            if(it.profilePic != null && it.profilePic != ""){
                Picasso.get().load(it.profilePic).into(binding.vendorProfile)
            }
        }
    }

    private fun loadRatings(recyclerView: RecyclerView){
        recyclerView.layoutManager = LinearLayoutManager(this)
        val ratingRepository = RatingRepository()
        ratingRepository.getRatings(vendorId){
            adapter = RatingListAdapter(it,this, userId)
            recyclerView.adapter = adapter
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