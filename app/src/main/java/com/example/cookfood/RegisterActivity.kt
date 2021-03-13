package com.example.cookfood

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

/**
 * Created by SANJEET KUMAR on 13,March,2021, sk698166@gmail.com
 */

class RegisterActivity : AppCompatActivity() {
    var editUserEmail: EditText? = null
    var editUserPassword: EditText? = null
//    var registerButton: Button? = null
    lateinit var registerButton: Button
    var auth: FirebaseAuth? = null
    var userEmila: String? = null
    var password: String? = null
    var progressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        editUserEmail = findViewById(R.id.userEmail)
        editUserPassword = findViewById(R.id.passwordRegister)
        registerButton = findViewById(R.id.registerButton)
        registerButton.setOnClickListener(View.OnClickListener { userRegister() })
    }

    private fun userRegister() {
        userEmila = editUserEmail!!.text.toString()
        password = editUserPassword!!.text.toString()
        if (userEmila=="") {
            editUserEmail!!.error = "Please Enter Email !"
            editUserEmail!!.requestFocus()
            return
        } else if (password=="") {
            editUserPassword!!.error = "Please Enter Password !"
            editUserPassword!!.requestFocus()
            return
        } else if (password!!.length < 6) {
            Toast.makeText(this, "Password is Too Short !", Toast.LENGTH_SHORT).show()
        } else {
            registerUser()
        }
    }

    private fun registerUser() {
        progressDialog!!.setMessage("Registering Please Wait...")
        progressDialog!!.show()
        auth!!.createUserWithEmailAndPassword(userEmila!!, password!!).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this@RegisterActivity, "Sign up Successfully ! ", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this@RegisterActivity, "Something Went Wrong !", Toast.LENGTH_SHORT).show()
            }
            progressDialog!!.dismiss()
        }
    }
}