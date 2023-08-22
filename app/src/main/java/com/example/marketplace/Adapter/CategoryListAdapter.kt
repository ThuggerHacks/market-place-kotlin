package com.example.marketplace.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.Adapter.Listeners.OnCategoryClickListener
import com.example.marketplace.Adapter.ViewHolders.CategoryViewHolder
import com.example.marketplace.Model.Category
import com.example.marketplace.R

class CategoryListAdapter(val categoryList:List<Category>, val onCategoryClickListener: OnCategoryClickListener):RecyclerView.Adapter<CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_recycler_view,parent,false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
       val category = categoryList[position]
        holder.title.text = category.category_name
        holder.title.setOnClickListener {
            onCategoryClickListener.onClick(category)
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}