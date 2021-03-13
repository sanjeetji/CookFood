package com.example.cookfood

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookfood.Adapters.RecipeAdapter
import com.example.cookfood.model.RecipeModel
import java.util.*

/**
 * Created by SANJEET KUMAR on 13,March,2021, sk698166@gmail.com
 */

class RecipesActivity : AppCompatActivity() {
    var mDrawer: DrawerLayout? = null
    var mToggle: ActionBarDrawerToggle? = null
    lateinit var toolbar: Toolbar
    lateinit var recyclerView: RecyclerView
    var recipeAdapter: RecipeAdapter? = null
    var recipeModelArrayList: ArrayList<RecipeModel>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes)
        toolbar = findViewById(R.id.common_toolbar)
        setSupportActionBar(toolbar)
        recyclerView = findViewById(R.id.recipe_recycler)
        recyclerView.setLayoutManager(LinearLayoutManager(this))
        toolbar.setNavigationIcon(R.drawable.back_icon)
        toolbar.setNavigationOnClickListener(View.OnClickListener { onBackPressed() })
        recipesData
    }

    private val recipesData: Unit
        private get() {
            recipeModelArrayList = ArrayList()
            recipeModelArrayList!!.add(RecipeModel("Dal Curi", R.drawable.indin_one, "Rs. 550"))
            recipeModelArrayList!!.add(RecipeModel("Panner Tikka", R.drawable.indin_one, "Rs. 800"))
            recipeModelArrayList!!.add(RecipeModel("Dal Chawal", R.drawable.indin_one, "Rs. 200"))
            recipeModelArrayList!!.add(RecipeModel("Allu Puri", R.drawable.indin_one, "Rs. 350"))
            recipeModelArrayList!!.add(RecipeModel("Sabji Roti", R.drawable.indin_one, "Rs. 70.0"))
            recipeModelArrayList!!.add(RecipeModel("Pnaeer Malai", R.drawable.indin_one, "Rs. 1000"))
            recipeModelArrayList!!.add(RecipeModel("Dal Tadka", R.drawable.indin_one, "Rs. 680"))
            recipeAdapter = RecipeAdapter(recipeModelArrayList!!, this@RecipesActivity)
            recyclerView!!.adapter = recipeAdapter
        }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val menuItem = menu.findItem(R.id.search)
        val searchView = menuItem.actionView as SearchView
        searchView.setBackgroundColor(resources.getColor(R.color.white))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                recipeAdapter!!.getFilter().filter(newText)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}