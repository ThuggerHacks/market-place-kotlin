package com.example.marketplace.View

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.marketplace.Model.User
import com.example.marketplace.R
import com.example.marketplace.Repository.UserRepository
import com.example.marketplace.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private  lateinit var storage:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storage = this.getSharedPreferences("user", MODE_PRIVATE)

        //check if he is logged already
        if(storage?.getInt("id",0) != 0){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        binding.loginBtn.setOnClickListener {
            //login user
           loginHandler()
        }

        binding.registerBtnGo.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }

    }

    private fun loginHandler(){
        val userRepository: UserRepository = UserRepository()
        val email = binding.emailAddress.text.toString()
        val password = binding.passwsordUser.text.toString()
        val user = User(0,"",email,password)
        binding.loginBtn.setText("CARREGANDO...")
        try {
            userRepository.login(user) { user ->
                if(user?.id != 0){
                    val editor = storage.edit()
                    editor.putInt("id",user.id)
                    editor.apply()
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                    binding.loginBtn.setText("ENTRAR")
                }else{
                    Toast.makeText(this, "Dados Invalidos", Toast.LENGTH_LONG).show()
                    binding.loginBtn.setText("ENTRAR")
                }
            }
        }catch (e:Exception){
            Toast.makeText(this, "Houve um erro!", Toast.LENGTH_SHORT).show()
            binding.loginBtn.setText("ENTRAR")
        }
    }
}