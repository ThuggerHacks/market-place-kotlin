package com.example.marketplace.Adapter.ViewHolders

import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.R


class ProductViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val price = view.findViewById<TextView>(R.id.price_product)
    val image = view.findViewById<ImageView>(R.id.image_product)
    val title = view.findViewById<TextView>(R.id.title_product)
    val productContainer = view.findViewById<RelativeLayout>(R.id.product_container)
    val dots = view.findViewById<TextView>(R.id.dots)
}