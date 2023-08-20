package com.example.marketplace.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.Adapter.Listeners.OnRatingClickListener
import com.example.marketplace.Adapter.ViewHolders.RatingViewHolder
import com.example.marketplace.Model.Rating
import com.example.marketplace.R


class RatingListAdapter(var ratingList:List<Rating>, val onRatingClickListener: OnRatingClickListener):RecyclerView.Adapter<RatingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rating_recycler_view,parent,false)
        return RatingViewHolder(view)
    }

    override fun onBindViewHolder(holder: RatingViewHolder, position: Int) {
       val rating = ratingList[position]
        holder.comment.setText(rating.comment)
        holder.user.setText(rating.user)
        holder.starts.setText(rating.rate)
        holder.btnDel.setOnClickListener{
            onRatingClickListener.onClick(rating)
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