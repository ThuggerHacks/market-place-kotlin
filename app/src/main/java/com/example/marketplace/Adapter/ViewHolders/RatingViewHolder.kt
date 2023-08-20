package com.example.marketplace.Adapter.ViewHolders

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.R

class RatingViewHolder(view:View):RecyclerView.ViewHolder(view) {
    val user = view.findViewById<TextView>(R.id.user_rater)
    val comment = view.findViewById<TextView>(R.id.comment_rate)
    val starts = view.findViewById<TextView>(R.id.rating_star)
    val btnDel = view.findViewById<LinearLayout>(R.id.delete_btn)
}