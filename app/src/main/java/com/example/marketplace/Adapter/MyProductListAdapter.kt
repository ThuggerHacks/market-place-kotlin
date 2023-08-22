package com.example.marketplace.Adapter

import OnMyProductClickListener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.Adapter.ViewHolders.MyProductViewHolder
import com.example.marketplace.Model.Product
import com.example.marketplace.R

class MyProductListAdapter(var myProductList:List<Product>, val onMyProductClickListener: OnMyProductClickListener):RecyclerView.Adapter<MyProductViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyProductViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_my_product,parent,false)
        return MyProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyProductViewHolder, position: Int) {
       val product = myProductList[position]
        holder.title.setText(product.title)
        holder.viewBtn.setOnClickListener{
            onMyProductClickListener.onView(product)
        }

        holder.delBtn.setOnClickListener{
            onMyProductClickListener.onDelete(product)
        }

        holder.editBtn.setOnClickListener{
            onMyProductClickListener.onEdit(product)
        }
    }

    fun updateProductList(productList: List<Product>){
        this.myProductList = productList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return myProductList.size
    }
}