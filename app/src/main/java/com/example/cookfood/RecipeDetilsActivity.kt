package com.example.cookfood

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookfood.Adapters.CommentsAdapetr
import com.example.cookfood.RecipeDetilsActivity
import com.example.cookfood.model.CommentsModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*

class RecipeDetilsActivity : AppCompatActivity() {
    lateinit var imageView: ImageView
    lateinit var editName: TextView
    lateinit var editPrice: TextView
    lateinit var editDesciption: TextView
    lateinit var commentPostBtn: Button
    lateinit var commentGetBtn: Button
    var recyclerView: RecyclerView? = null
    var commentsAdapetr: CommentsAdapetr? = null
    lateinit var commentsArrayList: MutableList<CommentsModel>
    var sharedpreferences: SharedPreferences? = null
    var databaseArtists: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detils)
        databaseArtists = FirebaseDatabase.getInstance().getReference("Comments")
        sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE)
        imageView = findViewById(R.id.recipe_img_det)
        editName = findViewById(R.id.recipe_text_det)
        editPrice = findViewById(R.id.preiceTextView_dtl)
        editDesciption = findViewById(R.id.notes_dtl)
        commentPostBtn = findViewById(R.id.commentPostBtn)
        commentGetBtn = findViewById(R.id.commentGetBtn)
        imageView.setImageResource(intent.getIntExtra("image", 0))
        editName.setText(intent.getStringExtra("name"))
        editPrice.setText(intent.getStringExtra("price"))
        val addNote = "Restaurants in Kolkata, Kolkata Restaurants, Sinthi restaurants, " +
                "Best Sinthi restaurants, North Kolkata restaurants, North Indian Restaurants" +
                " in Kolkata, North Indian near me, North Indian Restaurants in North Kolkata," +
                " North Indian Restaurants in Sinthi, in Kolkata, near me, in North Kolkata," +
                " in Sinthi, in Kolkata, near me, in North Kolkata, in Sinthi, " +
                "in Kolkata, near me, in North Kolkata, in Sinthi, New Year Parties in Kolkata, Christmas' Special in Kolkata,"
        editDesciption.setText(addNote)
        if (FirebaseAuth.getInstance().currentUser == null) {
            commentPostBtn.setVisibility(View.INVISIBLE)
        }
        commentPostBtn.setOnClickListener(View.OnClickListener {
            openPostDialog()
            Toast.makeText(this@RecipeDetilsActivity, "Open Dialog !", Toast.LENGTH_SHORT).show()
        })
        commentGetBtn.setOnClickListener(View.OnClickListener { openGetCommentDialog() })
    }

    private fun openGetCommentDialog() {
        val alert = AlertDialog.Builder(this@RecipeDetilsActivity, android.R.style.Theme_DeviceDefault_Light)
        val mView = LayoutInflater.from(this@RecipeDetilsActivity).inflate(R.layout.get_comment_dialog, null)
        val recyclerView: RecyclerView = mView.findViewById(R.id.commentsGetRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val getComment = sharedpreferences!!.getString(Comment, "Null Values")
        commentsArrayList = ArrayList()
        databaseArtists!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (singleSnapshot in dataSnapshot.children) {
                    val commentsModel = singleSnapshot.getValue(CommentsModel::class.java)
                    val s = commentsModel.toString()
                    val commntIs = singleSnapshot.child("comment").value.toString()
                    commentsArrayList.add(CommentsModel(commntIs))
                    Log.e("=========", "Sanjeet Comment is :$commentsArrayList")
                }
                commentsAdapetr = CommentsAdapetr(commentsArrayList, this@RecipeDetilsActivity)
                recyclerView.adapter = commentsAdapetr
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException())
            }
        })
        /*commentsAdapetr = new CommentsAdapetr(commentsArrayList,this);
        recyclerView.setAdapter(commentsAdapetr);*/alert.setView(mView)
        val alertDialog = alert.create()
        alertDialog.show()
    }

    private fun openPostDialog() {
        val editor = sharedpreferences!!.edit()
        val answer: EditText
        val sendMsg: Button
        val alert = AlertDialog.Builder(this@RecipeDetilsActivity, android.R.style.Theme_DeviceDefault_Light)
        val mView = LayoutInflater.from(this@RecipeDetilsActivity).inflate(R.layout.add_comment_dialog, null)
        answer = mView.findViewById(R.id.comment_editText)
        sendMsg = mView.findViewById(R.id.postComment)
        alert.setView(mView)
        val alertDialog = alert.create()
        alertDialog.show()
        sendMsg.setOnClickListener {
            val comment = answer.text.toString()
            val id = databaseArtists!!.push().key
            val commentsModel = CommentsModel(comment)
            databaseArtists!!.child(id!!).setValue(commentsModel)
            editor.putString(Comment, comment)
            editor.commit()
            editor.apply()
            Toast.makeText(this@RecipeDetilsActivity, "Comment Post Successful !", Toast.LENGTH_SHORT).show()
            alertDialog.dismiss()
        }
    }

    companion object {
        const val MyPREFERENCES = "MyCommentsPref"
        const val Comment = "commentKey"
    }
}