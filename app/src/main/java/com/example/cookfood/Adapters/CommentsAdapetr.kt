package com.example.cookfood.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookfood.R
import com.example.cookfood.model.CommentsModel

/**
 * Created by SANJEET KUMAR on 13,March,2021, sk698166@gmail.com
 */
class CommentsAdapetr(var comments: List<CommentsModel>, var context: Context) : RecyclerView.Adapter<CommentsAdapetr.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.comments_row_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.comment.text = comments[position].comment
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var comment: TextView

        init {
            comment = itemView.findViewById(R.id.comments_row)
        }
    }
}