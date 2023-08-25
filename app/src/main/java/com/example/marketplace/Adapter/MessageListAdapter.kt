package com.example.marketplace.Adapter

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.Adapter.ViewHolders.MessageViewHolder
import com.example.marketplace.Model.Message
import com.example.marketplace.R
import com.example.marketplace.Repository.UserRepository
import com.squareup.picasso.Picasso

class MessageListAdapter(var messageList:List<Message>, val context:Context,val userId:Int = 0):RecyclerView.Adapter<MessageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_recycler_view_chat,parent,false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messageList[position]

        if(message.senderId != userId){
            holder.bolb.setBackgroundColor(ContextCompat.getColor(context, R.color.black_opac))
            UserRepository().getUserOne(message.senderId){
               if(it.profilePic != null){
                   Picasso.get().load(it.profilePic).into(holder.profile)
                   holder.profile.setBackgroundColor(ContextCompat.getColor(context,R.color.transparent))
               }else{
                   holder.profile.setImageResource(R.drawable.ic_baseline_person_24_blue)
                   holder.profile.setBackgroundColor(R.drawable.product_border_radius_category)
               }
            }
        }else{
            holder.bolb.setBackgroundColor(ContextCompat.getColor(context, R.color.blue))
            UserRepository().getUserOne(userId){
                if(it.profilePic != null){
                    Picasso.get().load(it.profilePic).into(holder.profile)
                    holder.profile.setBackgroundColor(ContextCompat.getColor(context,R.color.transparent))
                }else{
                    holder.profile.setImageResource(R.drawable.ic_baseline_person_24_blue)
                    holder.profile.setBackgroundColor(R.drawable.product_border_radius_category)
                }
            }
        }
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