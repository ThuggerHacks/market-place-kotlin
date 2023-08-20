package com.example.marketplace.Adapter.ViewHolders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.R

class CategoryViewHolder(view:View): RecyclerView.ViewHolder(view) {
    val title = view.findViewById<TextView>(R.id.category_title)
}