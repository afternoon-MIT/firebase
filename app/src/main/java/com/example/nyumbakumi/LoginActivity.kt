package com.example.nyumbakumi

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var edtemail2: EditText
    lateinit var edtpassword2: EditText
    lateinit var btnlogin2: Button
    lateinit var txtregister2: TextView
    lateinit var txtreset: TextView
    lateinit var mAuth: FirebaseAuth
    lateinit var progress: ProgressDialog

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        edtemail2 = findViewById(R.id.edtemail2)
        edtpassword2 = findViewById(R.id.edtpassword2)
        btnlogin2 = findViewById(R.id.btnlogin2)
        txtregister2 = findViewById(R.id.txtregister2)
        txtreset = findViewById(R.id.txtreset)
        mAuth = FirebaseAuth.getInstance()
        progress = ProgressDialog(this)
        progress.setTitle("Loading")
        progress.setMessage("Please wait...")
        btnlogin2.setOnClickListener {
            var email = edtemail2.text.toString().trim()
            var password = edtpassword2.text.toString().trim()

            // Check if the user is submitting empty files

            if (email.isEmpty()) {
                edtemail2.setError("Please fill this input")
                edtemail2.requestFocus()
            } else if (password.isEmpty()) {
                edtpassword2.setError("Please fill the input")
                edtpassword2.requestFocus()
            } else {
                // Proceed to register the user
                progress.show()
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    progress.dismiss()
                    if (it.isSuccessful) {
                        Toast.makeText(
                            this, "Registration  successful",
                            Toast.LENGTH_SHORT
                        ).show()
                        mAuth.signOut()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()

                    } else {
                        displaymessage("Error", it.exception!!.message.toString())
                    }
                }
            }
        }
        txtregister2.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        txtreset.setOnClickListener {

        }
    }

    fun displaymessage(title: String, message: String) {
        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton("Ok", null)
        alertDialog.create().show()
    }
}