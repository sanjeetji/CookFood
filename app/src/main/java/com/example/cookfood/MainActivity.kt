package com.example.cookfood

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookfood.Adapters.CategorieAdapter
import com.example.cookfood.model.CategorieModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import java.util.*

/**
 * Created by SANJEET KUMAR on 13,March,2021, sk698166@gmail.com
 */
//sanjeet Kumar
class MainActivity : AppCompatActivity() {
    var mDrawer: DrawerLayout? = null
    var mRecyclerView: RecyclerView? = null
    var mToggle: ActionBarDrawerToggle? = null
    var nav: NavigationView? = null
    var toolbar: Toolbar? = null
    var categorieAdapter: CategorieAdapter? = null
    var auth: FirebaseAuth? = null
    var categorieModelList: ArrayList<CategorieModel>? = null
    lateinit var profile: ImageView
    lateinit var userNameheader: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        mDrawer = findViewById<View>(R.id.drawer) as DrawerLayout
        mRecyclerView = findViewById(R.id.categorie_recyclerView)
        nav = findViewById<View>(R.id.navHeader) as NavigationView
        val header = nav!!.getHeaderView(0)
        profile = header.findViewById(R.id.profileImage)
        userNameheader = header.findViewById(R.id.userNameHeader)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val menu = nav!!.menu
        val login = menu.findItem(R.id.login)
        profile.setImageResource(R.drawable.color_gmail_icpn)
        userNameheader.setText("Sanjeet Kumar")
        if (FirebaseAuth.getInstance().currentUser != null) {
            login.setIcon(R.drawable.logout)
            login.title = "Log out"
        }
        mToggle = ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.open, R.string.close)
        mToggle!!.drawerArrowDrawable.color = resources.getColor(R.color.white)
        mDrawer!!.addDrawerListener(mToggle!!)
        mToggle!!.syncState()
        nav!!.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> mDrawer!!.closeDrawer(GravityCompat.START)
                R.id.login -> if (FirebaseAuth.getInstance().currentUser != null) {
                    login.setIcon(R.drawable.login)
                    login.title = "Log in"
                    auth!!.signOut()
                    mDrawer!!.closeDrawer(GravityCompat.START)
                } else {
                    startActivity(Intent(applicationContext, LoginActivity::class.java))
                }
            }
            true
        }
        categoriesItems
    }

    private val categoriesItems: Unit
        private get() {
            categorieModelList = ArrayList()
            categorieModelList!!.add(CategorieModel(R.drawable.categorie_one, "Indian Cuisine"))
            categorieModelList!!.add(CategorieModel(R.drawable.categorie_two, "American Cuisine"))
            categorieModelList!!.add(CategorieModel(R.drawable.categorie_one, "Andorra Cuisine"))
            categorieModelList!!.add(CategorieModel(R.drawable.categorie_two, "Argentina Cuisine"))
            categorieModelList!!.add(CategorieModel(R.drawable.categorie_one, "Austria Cuisine"))
            categorieModelList!!.add(CategorieModel(R.drawable.categorie_two, "Belgium Cuisine"))
            categorieModelList!!.add(CategorieModel(R.drawable.categorie_one, "Brazil Cuisine"))
            categorieModelList!!.add(CategorieModel(R.drawable.categorie_two, "China Cuisine"))
            mRecyclerView!!.layoutManager = GridLayoutManager(this@MainActivity, 2)
            categorieAdapter = CategorieAdapter(categorieModelList!!, this@MainActivity)
            mRecyclerView!!.adapter = categorieAdapter
            categorieAdapter!!.notifyDataSetChanged()
        }
}