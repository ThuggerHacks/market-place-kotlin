package com.example.marketplace.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.Adapter.Listeners.OnChatClickListener
import com.example.marketplace.Adapter.ViewHolders.ChatViewHolder
import com.example.marketplace.Model.Chat
import com.example.marketplace.R
import com.example.marketplace.Repository.MessageRepository
import com.example.marketplace.Repository.ProductRepository
import com.example.marketplace.Repository.UserRepository
import com.squareup.picasso.Picasso

class ChatListAdapter(var chatList:List<Chat>, val onChatClickListener: OnChatClickListener): RecyclerView.Adapter<ChatViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
     val view = LayoutInflater.from(parent.context)
         .inflate(R.layout.message_recycler_view,parent,false)

        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = chatList[position]

        MessageRepository().getAllByChat(chat.id){
         if(it.get(0).message.toString().length > 35){
             holder.message.text = it.get(0).message.toString().substring(0,35)
         }else{
             holder.message.text = it.get(0).message.toString()
         }
            holder.date.text =  it.get(0).date.toString()
            ProductRepository().getOneProduct(chat.productId){ p ->
               if(p.title.toString().length > 20){
                   holder.topic.text = p.title.toString().substring(0,20)+"..."
               }else{
                   holder.topic.text = p.title.toString()
               }

                if(p.coverUrl != null && p.coverUrl != ""){
                    Picasso.get().load(p.coverUrl).into(holder.profile)
                }else{
                    holder.profile.setImageResource(R.drawable.ic_baseline_person_24)
                }
            }

            UserRepository().getUserOne(it.get(0).senderId){ u ->
                holder.user.text = u.name.toString()+":"
            }

        }

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