package com.example.marketplace.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
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
import com.example.marketplace.View.Fragments.AccountFragment
import com.example.marketplace.View.Fragments.AddFragment
import com.example.marketplace.View.Fragments.HomeFragment
import com.example.marketplace.View.Fragments.MessageFragment
import com.example.marketplace.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var result:ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            bundle.putString("id",intent.getStringExtra("param"))
            fragment.arguments = bundle
            showFragment(fragment)
        }

    }


    fun showChangeFragments(navigation: BottomNavigationView){
        //change fragments
        navigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home_tab -> showFragment(HomeFragment())
                R.id.chat_tab -> showFragment(MessageFragment())
                R.id.add_tab -> showFragment(AddFragment())
                R.id.person_tab -> showFragment(AccountFragment())
                R.id.search_tab -> {
                    //do something for search button
                    val view = layoutInflater.inflate(R.layout.bottom_sheet,null)
                    val dialog = BottomSheetDialog(this)
                    dialog.setContentView(view)
                    dialog.show()
                    //hide the dialog on click of the ok button
                    val btn_okay = view.findViewById<Button>(R.id.ok_btn)
                    btn_okay.setOnClickListener {
                        dialog.hide()
                    }
                }
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