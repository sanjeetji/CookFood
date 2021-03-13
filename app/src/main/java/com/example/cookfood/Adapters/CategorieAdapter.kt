package com.example.cookfood.Adapters

import com.example.cookfood.model.CategorieModel
import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.cookfood.R
import com.bumptech.glide.Glide
import android.content.Intent
import android.view.View
import android.widget.ImageView
import com.example.cookfood.RecipesActivity
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import android.widget.TextView
import java.util.ArrayList

/**
 * Created by SANJEET KUMAR on 13,March,2021, sk698166@gmail.com
 */

class CategorieAdapter(var categorieModels: ArrayList<CategorieModel>, var context: Activity) : RecyclerView.Adapter<CategorieAdapter.MyViewHolder>() {
    var isFav = true
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.categorie_row_items, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context)
                .load(categorieModels[position].cuisine)
                .centerCrop()
                .placeholder(R.drawable.no_photo)
                .into(holder.categorieImg)
        holder.categorieName.text = categorieModels[position].cuisineName
        holder.categorieImg.setOnClickListener {
            val intent = Intent(context, RecipesActivity::class.java)
            context.startActivity(intent)
        }
        holder.favCategorie.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser != null) {
                isFav = if (isFav) {
                    holder.favCategorie.setBackgroundResource(R.drawable.favorite)
                    Toast.makeText(context, "Add to Favorite ! ", Toast.LENGTH_SHORT).show()
                    false
                } else {
                    holder.favCategorie.setBackgroundResource(R.drawable.blank_favorite)
                    Toast.makeText(context, "Remove Favorite ! ", Toast.LENGTH_SHORT).show()
                    true
                }
            } else {
                Toast.makeText(context, "You Have To Login !", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return categorieModels.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var categorieImg: ImageView
        var categorieName: TextView
        var favCategorie: TextView

        init {
            categorieImg = itemView.findViewById(R.id.categorieImg)
            categorieName = itemView.findViewById(R.id.categoreName)
            favCategorie = itemView.findViewById(R.id.favCategorie)
        }
    }
}