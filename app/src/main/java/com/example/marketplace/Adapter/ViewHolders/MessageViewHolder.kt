package com.example.marketplace.Adapter.ViewHolders

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.R

class MessageViewHolder(view:View): RecyclerView.ViewHolder(view) {
    val message = view.findViewById<TextView>(R.id.message_bolb)
    val bolb = view.findViewById<LinearLayout>(R.id.bolb_container)
    val profile = view.findViewById<ImageView>(R.id.user_profil_messge_pic)
    val delBtn = view.findViewById<ImageView>(R.id.delete_btn_message)
    val image = view.findViewById<ImageView>(R.id.image_chat)
}