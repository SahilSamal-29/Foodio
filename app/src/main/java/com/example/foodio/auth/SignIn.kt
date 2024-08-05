package com.example.foodio.auth

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.foodio.MainActivity
import com.example.foodio.R
import com.example.foodio.databinding.ActivitySignInBinding
import com.example.foodio.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class SignIn : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var binding: ActivitySignInBinding

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)
        binding = DataBindingUtil.setContentView( this , R.layout.activity_sign_in)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.sign_in)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = FirebaseAuth.getInstance()
        val dontHaveAccount = findViewById<TextView>(R.id.textView2)
        dontHaveAccount.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
//        val signIn = findViewById<Button>(R.id.btnSignIn)
        binding.btnSignIn.setOnClickListener{
            val email = binding.etName.text.toString().trim()
            val password = binding.etPassword.text.toString()
            when{
                email.isEmpty() -> {
                    binding.etName.error = "Please enter your email"
                    binding.etName.requestFocus()
                    return@setOnClickListener
                }
                password.isEmpty() -> {
                    binding.etPassword.error = "Please enter your password"
                    binding.etPassword.requestFocus()
                    return@setOnClickListener
                }
                else -> {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success")
                                val user = auth.currentUser
//                                updateUI(user)
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.exception)
                                Toast.makeText(
                                    baseContext,
                                    "Authentication failed.",
                                    Toast.LENGTH_SHORT,
                                ).show()
//                                updateUI(null)
                            }
                        }
                }
            }
        }
    }
}