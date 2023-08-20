package com.example.marketplace.Adapter.ViewHolders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.R

class MessageViewHolder(view:View): RecyclerView.ViewHolder(view) {
    val message = view.findViewById<TextView>(R.id.message_bolb)
}