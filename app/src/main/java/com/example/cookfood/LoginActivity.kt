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
class LoginActivity : AppCompatActivity() {
    var editEmail: EditText? = null
    var editPassword: EditText? = null
    lateinit var editLogin: Button
    lateinit var editRegister: Button
    var auth: FirebaseAuth? = null
    var userName: String? = null
    var password: String? = null
    var progressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        editEmail = findViewById(R.id.userNameText)
        editPassword = findViewById(R.id.passwordEditText)
        editLogin = findViewById(R.id.loginButton)
        editRegister = findViewById(R.id.registerButtonLogin)
        progressDialog = ProgressDialog(this)
        auth = FirebaseAuth.getInstance()
        editRegister.setOnClickListener(View.OnClickListener { startActivity(Intent(this@LoginActivity, RegisterActivity::class.java)) })
        auth = FirebaseAuth.getInstance()
        editLogin.setOnClickListener(View.OnClickListener { userLogin() })
    }

    private fun userLogin() {
        userName = editEmail!!.text.toString()
        password = editPassword!!.text.toString()
        if (userName == "") {
            editEmail!!.error = "Please Enter User Name !"
            editEmail!!.requestFocus()
            return
        } else if (password == "") {
            editPassword!!.error = "Please Enter Password !"
            editPassword!!.requestFocus()
            return
        } else {
            LoginUser()
        }
    }

    private fun LoginUser() {
        progressDialog!!.setMessage("Login Please Wait...")
        progressDialog!!.show()
        auth!!.signInWithEmailAndPassword(userName!!, password!!).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this@LoginActivity, "Login Successfully !", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this@LoginActivity, "Please Check your email or Password !", Toast.LENGTH_SHORT).show()
            }
            progressDialog!!.dismiss()
        }
    }
}