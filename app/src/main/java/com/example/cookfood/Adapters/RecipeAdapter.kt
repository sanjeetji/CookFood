package com.example.cookfood.Adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cookfood.R
import com.example.cookfood.RecipeDetilsActivity
import com.example.cookfood.model.RecipeModel
import com.google.firebase.auth.FirebaseAuth
import java.util.*

/**
 * Created by SANJEET KUMAR on 13,March,2021, sk698166@gmail.com
 */

class RecipeAdapter(var recipeModelArrayList: ArrayList<RecipeModel>, var context: Activity) : RecyclerView.Adapter<RecipeAdapter.MyViewHolder>(), Filterable {
    lateinit var facvRecipe: List<RecipeModel>
    var isFav = true
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recipe_row_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context)
                .load(recipeModelArrayList[position].img)
                .centerCrop()
                .placeholder(R.drawable.no_photo)
                .into(holder.imageView)
        holder.text.text = recipeModelArrayList[position].name
        holder.recipePrice.text = recipeModelArrayList[position].price
        holder.favText.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser != null) {
                isFav = if (isFav) {
                    holder.favText.setBackgroundResource(R.drawable.favorite)
                    Toast.makeText(context, "Add to Favorite ! ", Toast.LENGTH_SHORT).show()
                    false
                } else {
                    holder.favText.setBackgroundResource(R.drawable.blank_favorite)
                    Toast.makeText(context, "Remove Favorite ! ", Toast.LENGTH_SHORT).show()
                    true
                }
            } else {
                Toast.makeText(context, "Please Login !", Toast.LENGTH_SHORT).show()
            }
        }
        holder.imageView.setOnClickListener {
            val i = Intent(context, RecipeDetilsActivity::class.java)
            i.putExtra("image", recipeModelArrayList[position].img)
            i.putExtra("name", recipeModelArrayList[position].name)
            i.putExtra("price", recipeModelArrayList[position].price)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return recipeModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var text: TextView
        var favText: TextView
        var recipePrice: TextView

        init {
            imageView = itemView.findViewById(R.id.recipe_img)
            text = itemView.findViewById(R.id.recipeName)
            favText = itemView.findViewById(R.id.fav)
            recipePrice = itemView.findViewById(R.id.recipePrice)
        }
    }

    override fun getFilter(): Filter {
        return filter
    }

    private var filter: Filter = object : Filter() {
        override fun performFiltering(keyword: CharSequence): FilterResults {
            val filterData = ArrayList<RecipeModel>()
            if (keyword.toString()=="") {
                filterData.addAll(facvRecipe)
            } else {
                for (obj in facvRecipe) {
                    if (obj.name.toString().toLowerCase().contains(keyword.toString().toLowerCase())) {
                        filterData.add(obj)
                    }
                }
            }
            val results = FilterResults()
            results.values = filterData
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            recipeModelArrayList.clear()
            recipeModelArrayList.addAll((results.values as ArrayList<RecipeModel>))
            notifyDataSetChanged()
        }
    }

    init {
        facvRecipe = ArrayList(recipeModelArrayList)
    }
}