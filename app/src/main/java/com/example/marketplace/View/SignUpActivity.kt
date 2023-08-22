package com.example.marketplace.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.marketplace.Model.User
import com.example.marketplace.Repository.UserRepository
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

        binding.regiterBtn.setOnClickListener {
            registerUser()
        }
    }


    fun registerUser(){
        val userRepository = UserRepository()
        val name = binding.nameUser.text.toString()
        val email = binding.emailAddress.text.toString()
        val password = binding.passwsordUser.text.toString()
        val cpassword = binding.passwsordUserC.text.toString()
        val location = binding.userLocation.text.toString()
        val phone = binding.userCell.text.toString()

        val user = User(0,name,email,password,phone,location)

        binding.regiterBtn.setText("CARREGANDO...")

        if(
            name.trim().length == 0
            || email.trim().length == 0
            || password.trim().length == 0
            || location.trim().length == 0
            || phone.trim().length == 0){
            binding.regiterBtn.setText("REGISTAR")
            Toast.makeText(this,"Por favor preencha todos os campos",Toast.LENGTH_LONG).show()
        }else if(cpassword != password){
            binding.regiterBtn.setText("REGISTAR")
            Toast.makeText(this,"As senhas devem ser iguais",Toast.LENGTH_LONG).show()
        }else {
            try {
                userRepository.registerUser(user) { user ->
                    if(user?.id != 0 ){
                        Toast.makeText(this,"Utilizador registado com sucesso!",Toast.LENGTH_LONG).show()
                        binding.regiterBtn.setText("REGISTAR")
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        },1000)
                    }else if(user?.error != ""){
                        binding.regiterBtn.setText("REGISTAR")
                        Toast.makeText(this,user?.error,Toast.LENGTH_LONG).show()
                    }else{
                        binding.regiterBtn.setText("REGISTAR")
                        Toast.makeText(this,"Houve um erro, volte a tentar mais tarde",Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Houve um erro", Toast.LENGTH_LONG).show()
                binding.regiterBtn.setText("REGISTAR")
            }
        }
    }
}