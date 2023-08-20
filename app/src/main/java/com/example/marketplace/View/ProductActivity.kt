package com.example.marketplace.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.marketplace.R
import com.example.marketplace.databinding.ActivityProductBinding

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

    }
}