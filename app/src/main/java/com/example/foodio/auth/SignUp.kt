package com.example.foodio.auth

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.foodio.MainActivity
import com.example.foodio.R
import com.example.foodio.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class SignUp : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        binding = DataBindingUtil.setContentView( this , R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        binding.btnSignUp.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()
            when {
                name.isEmpty() -> {
                    binding.etName.error = "Please enter your name"
                    binding.etName.requestFocus()
                    return@setOnClickListener
                }
                email.isEmpty() -> {
                    binding.etEmail.error = "Please enter your email"
                    binding.etEmail.requestFocus()
                    return@setOnClickListener
                }
                password.isEmpty() -> {
                    binding.etEmail.error = "Please enter your email"
                    binding.etEmail.requestFocus()
                    return@setOnClickListener
                }
                else -> {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success")
                                val user = auth.currentUser
                                updateUI(user)
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.exception)
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

    private fun updateUI(user: FirebaseUser?) {
        binding.etName.text.clear()
        binding.etEmail.text.clear()
        binding.etPassword.text.clear()
    }
}