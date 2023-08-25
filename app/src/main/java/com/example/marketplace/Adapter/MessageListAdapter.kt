package com.example.marketplace.Adapter

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.Adapter.Listeners.OnMessageClickListener
import com.example.marketplace.Adapter.ViewHolders.MessageViewHolder
import com.example.marketplace.Model.Message
import com.example.marketplace.R
import com.example.marketplace.Repository.UserRepository
import com.squareup.picasso.Picasso

class MessageListAdapter(var messageList:List<Message>, val context:Context,val userId:Int = 0,val onMessageClickListener: OnMessageClickListener):RecyclerView.Adapter<MessageViewHolder>() {
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
            //listeners

            holder.bolb.setOnLongClickListener{
                if(holder.delBtn.visibility == View.GONE){
                    holder.delBtn.visibility = View.VISIBLE
                }else{
                    holder.delBtn.visibility = View.GONE
                }
                true
            }

            holder.delBtn.setOnClickListener{
                onMessageClickListener.onClick(message)
                holder.delBtn.visibility = View.GONE
            }
            /////////
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

        if(message.photoAttach != "" && message.photoAttach != null){
            Picasso.get().load(message.photoAttach).into(holder.image)
            holder.message.visibility = View.GONE
            holder.image.visibility = View.VISIBLE
        }else{
            holder.message.visibility = View.VISIBLE
            holder.image.visibility = View.GONE
            holder.message.text = message.message
        }
    }

    fun updateList(messageList: List<Message>){
        this.messageList = messageList
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
       return messageList.size
    }
}