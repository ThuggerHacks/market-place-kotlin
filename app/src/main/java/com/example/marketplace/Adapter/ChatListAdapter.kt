package com.example.marketplace.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.Adapter.Listeners.OnChatClickListener
import com.example.marketplace.Adapter.ViewHolders.ChatViewHolder
import com.example.marketplace.Model.Chat
import com.example.marketplace.R

class ChatListAdapter(var chatList:List<Chat>, val onChatClickListener: OnChatClickListener): RecyclerView.Adapter<ChatViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
     val view = LayoutInflater.from(parent.context)
         .inflate(R.layout.message_recycler_view,parent,false)

        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = chatList[position]
        holder.message.text = chat.message
        holder.topic.text = chat.topic
        holder.user.text = chat.sender+": "
        holder.container.setOnClickListener{
            onChatClickListener.onClick(chat)
        }
    }

    fun updateChat(chatList: List<Chat>){
        this.chatList = chatList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
      return chatList.size
    }
}