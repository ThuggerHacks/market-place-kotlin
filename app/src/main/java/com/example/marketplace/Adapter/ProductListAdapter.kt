package com.example.marketplace.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.marketplace.Adapter.Listeners.OnProductClickListener
import com.example.marketplace.Adapter.ViewHolders.ProductViewHolder
import com.example.marketplace.Model.Product
import com.example.marketplace.R
import com.squareup.picasso.Picasso

class ProductListAdapter(var productList: List<Product>, val onClickListener: OnProductClickListener): RecyclerView.Adapter<ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.product_recycler_view, parent,false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.price.text = "${product.price.toString()} MT"
       if(product.title.toString().length > 12){
           holder.title.text = product.title?.toString()?.substring(0,12)
       }else{
           holder.dots.visibility = View.GONE
           holder.title.text = product.title
       }
        Picasso.get().load(product.coverUrl).into(holder.image)
        holder.productContainer.setOnClickListener{
            onClickListener.onClick(product)
        }

    }

    fun updateProductList(productList: List<Product>){
        this.productList = productList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}