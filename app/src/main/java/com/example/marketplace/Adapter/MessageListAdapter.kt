package com.example.marketplace.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.Adapter.ViewHolders.MessageViewHolder
import com.example.marketplace.Model.Message
import com.example.marketplace.R

class MessageListAdapter(var messageList:List<Message>):RecyclerView.Adapter<MessageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_recycler_view_chat,parent,false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messageList[position]
        holder.message.setText(message.message)
    }

    fun updateList(messageList: List<Message>){
        this.messageList = messageList
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
       return messageList.size
    }
}