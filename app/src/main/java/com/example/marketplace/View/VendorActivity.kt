package com.example.marketplace.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.example.marketplace.R
import com.example.marketplace.databinding.ActivityVendorBinding

class VendorActivity : AppCompatActivity(), OnRatingClickListener {
    private lateinit var binding: ActivityVendorBinding
    private lateinit var adapter: RatingListAdapter
    private lateinit var list:ArrayList<Rating>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVendorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //toolbar configs
        val toolbarLabel = findViewById<TextView>(R.id.toolbar_label)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.custom_toolbar)
        toolbarLabel.setText("Braimo Selimane")
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //spinner config
        val spinner = binding.spinnerRating
        val spinnerData = arrayOf("1","2","3","4","5")
        spinner.adapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,spinnerData)

        //load ratings
        loadRatings(binding.ratingContainerRecyclerView)

        //logout
        val logoutBtn = findViewById<ImageView>(R.id.logout_pic_toolbar)
        logoutBtn.setOnClickListener{
            logout()
        }

    }

    override fun onClick(rating: Rating) {
       Toast.makeText(this,rating.comment,Toast.LENGTH_SHORT).show()
    }

    private fun loadRatings(recyclerView: RecyclerView){
        list = ArrayList()
        list.add(Rating("Braimo Selimane","Nao gostei, Burlador","2"))
        list.add(Rating("Sakif Jr","Nao gostei, Burlador","1"))
        list.add(Rating("Xafik Jr","Me burlou tambem","1"))
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RatingListAdapter(list,this)
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