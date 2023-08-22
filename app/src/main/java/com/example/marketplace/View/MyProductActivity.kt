package com.example.marketplace.View

import OnMyProductClickListener
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.Adapter.MyProductListAdapter
import com.example.marketplace.Model.Product
import com.example.marketplace.R
import com.example.marketplace.Repository.ProductRepository
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


        //logout
        val logoutBtn = findViewById<ImageView>(R.id.logout_pic_toolbar)
        logoutBtn.setOnClickListener{
            logout()
        }
    }

    override fun onView(product: Product) {
       val intent = Intent(this,ProductActivity::class.java)
        intent.putExtra("productId",product.id.toString())
        startActivity(intent)
    }

    override fun onDelete(product: Product) {
        val service = ProductRepository()
        service.deleteOneProduct(product.id){
            if(it?.error != null){
                Toast.makeText(this,it?.error ,Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Produto apagado com sucesso!", Toast.LENGTH_SHORT).show()
             service.getProductUser(it.userId){
                 adapter.updateProductList(it)
             }
            }
        }
    }

    override fun onEdit(product: Product) {
        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra("param","1")
        intent.putExtra("data","${product.id.toString()}")
        startActivity(intent)
    }

    private fun loadMyProducts(recyclerView: RecyclerView){
        val service = ProductRepository()
        val storage = this.getSharedPreferences("user", MODE_PRIVATE)
        val userId = storage.getInt("id",0)
        recyclerView.layoutManager = LinearLayoutManager(this)
        service.getProductUser(userId){
            adapter = MyProductListAdapter(it, this)
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