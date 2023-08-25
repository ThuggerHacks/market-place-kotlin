package com.example.marketplace.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.Adapter.Listeners.OnRatingClickListener
import com.example.marketplace.Adapter.ViewHolders.RatingViewHolder
import com.example.marketplace.Model.Rating
import com.example.marketplace.R
import com.example.marketplace.Repository.UserRepository


class RatingListAdapter(var ratingList:List<Rating>, val onRatingClickListener: OnRatingClickListener, val userId:Int):RecyclerView.Adapter<RatingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rating_recycler_view,parent,false)
        return RatingViewHolder(view)
    }

    override fun onBindViewHolder(holder: RatingViewHolder, position: Int) {
       val rating = ratingList[position]
        holder.comment.setText(rating.comment)

        UserRepository().getUserOne(rating.raterId){
            holder.user.setText(it.name)
        }
        holder.starts.setText(rating.ratingNumber.toString())

        if(userId != rating.raterId){
            holder.btnDel.visibility = View.GONE
        }

        holder.btnDel.setOnClickListener{
            onRatingClickListener.onClick(rating)
            holder.btnDel.visibility = View.GONE
        }

        holder.bolb.setOnLongClickListener{
            if(userId == rating.raterId){
               if (holder.btnDel.visibility == View.VISIBLE){
                   holder.btnDel.visibility = View.GONE
               }else{
                   holder.btnDel.visibility = View.VISIBLE
               }
            }
            true
        }
    }

    fun updateRating(ratingList: List<Rating>){
        this.ratingList = ratingList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return ratingList.size
    }
}