package com.example.marketplace.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.Adapter.Listeners.OnCategoryClickListener
import com.example.marketplace.Adapter.ViewHolders.CategoryViewHolder
import com.example.marketplace.Model.Category
import com.example.marketplace.R

class CategoryListAdapter(val categoryList:List<Category>, val onCategoryClickListener: OnCategoryClickListener):RecyclerView.Adapter<CategoryViewHolder>() {
    private var selectedItemPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_recycler_view,parent,false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]
        holder.title.text = category.category_name

        val isSelected = position == selectedItemPosition

        // Update the background color based on selection
        if (isSelected) {
            holder.container.setBackgroundResource(R.drawable.border_selected_category)
        } else {
            holder.container.setBackgroundResource(R.drawable.product_border_radius_category)
        }

        // Set click listener
        holder.container.setOnClickListener {
            // Update selected item position
            selectedItemPosition = position

            // Trigger UI update for the clicked item
            notifyDataSetChanged()

            // Perform your click action
            onCategoryClickListener.onClick(category)
        }
    }



    override fun getItemCount(): Int {
        return categoryList.size
    }
}