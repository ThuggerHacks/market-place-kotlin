package com.example.marketplace.Adapter.ViewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.R

class MyProductViewHolder(view:View): RecyclerView.ViewHolder(view) {
    val editBtn = view.findViewById<ImageView>(R.id.my_edit)
    val delBtn = view.findViewById<ImageView>(R.id.my_delete)
    val viewBtn = view.findViewById<ImageView>(R.id.my_view)
    val title = view.findViewById<TextView>(R.id.my_title)
}