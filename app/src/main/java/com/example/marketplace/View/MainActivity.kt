package com.example.marketplace.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.Adapter.CategoryListAdapter
import com.example.marketplace.Adapter.Listeners.OnCategoryClickListener
import com.example.marketplace.Model.Category
import com.example.marketplace.R
import com.example.marketplace.Repository.ProductRepository
import com.example.marketplace.View.Fragments.AccountFragment
import com.example.marketplace.View.Fragments.AddFragment
import com.example.marketplace.View.Fragments.HomeFragment
import com.example.marketplace.View.Fragments.MessageFragment
import com.example.marketplace.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var result:ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //custom toolbar configs
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.custom_toolbar)
        setSupportActionBar(toolbar)
        //fragment function
        showFragment(HomeFragment())
        showChangeFragments(binding.bottomTabs)
        //check for data backing from activities so that different frames are displayed

        val intent = intent

        if(intent.getStringExtra("param")?.toInt() == 1){
            val fragment = AddFragment()
            val bundle = Bundle()
            bundle.putString("id",intent.getStringExtra("data"))
            fragment.arguments = bundle
            showFragment(fragment)
        }

        //logout
        val logoutBtn = findViewById<ImageView>(R.id.logout_pic_toolbar)
        logoutBtn.setOnClickListener{
            logout()
        }

    }

    fun logout(){
        val storage = this.getSharedPreferences("user", MODE_PRIVATE)
        val editor = storage.edit()
        editor.remove("id")
        editor.apply()
        startActivity(Intent(this,LoginActivity::class.java))
    }
    fun showChangeFragments(navigation: BottomNavigationView){
        //change fragments
        navigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home_tab -> showFragment(HomeFragment())
                R.id.chat_tab -> showFragment(MessageFragment())
                R.id.add_tab -> showFragment(AddFragment())
                R.id.person_tab -> showFragment(AccountFragment())
                else -> {
                    showFragment(HomeFragment())
                }
            }
            true
        }
    }

    fun showFragment(fragment:Fragment){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout,fragment)
        transaction.commit()
    }
}