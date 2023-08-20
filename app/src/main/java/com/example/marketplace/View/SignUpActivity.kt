package com.example.marketplace.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.marketplace.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.enterBtnGo.setOnClickListener {
            finish()
        }
    }
}