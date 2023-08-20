package com.example.marketplace.View

import OnMyProductClickListener
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.Adapter.MyProductListAdapter
import com.example.marketplace.Model.Product
import com.example.marketplace.R
import com.example.marketplace.databinding.ActivityMyProductBinding

class MyProductActivity : AppCompatActivity(), OnMyProductClickListener {
    private lateinit var binding:ActivityMyProductBinding
    private lateinit var adapter:MyProductListAdapter
    private lateinit var list:ArrayList<Product>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //toolbar config
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.custom_toolbar)
        val toolbarLabel = findViewById<TextView>(R.id.toolbar_label)
        toolbarLabel.setText("Os Meus Produtos")
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadMyProducts(binding.recyclerViewMyProducts)
    }

    override fun onView(product: Product) {
        Toast.makeText(this,"View: ${product.title}", Toast.LENGTH_SHORT).show()
    }

    override fun onDelete(product: Product) {
        Toast.makeText(this,"Delete: ${product.title}", Toast.LENGTH_SHORT).show()
    }

    override fun onEdit(product: Product) {
        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra("param","1")
        startActivity(intent)
        Toast.makeText(this,"Edit: ${product.title}", Toast.LENGTH_SHORT).show()
    }

    private fun loadMyProducts(recyclerView: RecyclerView){
        list = ArrayList()
        list.add(Product(1,1,"IPHONE PRO MAX","",100.0,"","",true))
        list.add(Product(1,1,"MACBOOK PRO","",100.0,"","",true))
        list.add(Product(1,1,"TECNO SPARK","",100.0,"","",true))

        adapter = MyProductListAdapter(list, this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}