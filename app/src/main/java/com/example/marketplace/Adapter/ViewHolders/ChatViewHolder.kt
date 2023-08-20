package com.example.marketplace.Adapter.ViewHolders

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.R

class ChatViewHolder(view:View): RecyclerView.ViewHolder(view) {
    val profile = view.findViewById<ImageView>(R.id.profile_message_pictufe)
    val topic = view.findViewById<TextView>(R.id.message_topic)
    val message = view.findViewById<TextView>(R.id.message_text)
    val user = view.findViewById<TextView>(R.id.sender_name)
    val container = view.findViewById<LinearLayout>(R.id.chat_container)
    val date = view.findViewById<TextView>(R.id.chat_date)
}