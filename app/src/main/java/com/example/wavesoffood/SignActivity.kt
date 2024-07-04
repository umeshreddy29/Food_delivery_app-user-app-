package com.example.wavesoffood

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wavesoffood.databinding.ActivitySignBinding
import com.example.wavesoffood.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var database : DatabaseReference
    private lateinit var email : String
    private lateinit var password : String
    private lateinit var userName : String

    private val binding: ActivitySignBinding by lazy {
        ActivitySignBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // initialize firebase auth
        auth = Firebase.auth
        // initialize firebase database reference
        database = Firebase.database.reference

        binding.toLogin.setOnClickListener{
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.createAccount.setOnClickListener{

            userName = binding.name.text.toString().trim()
            email = binding.email.text.toString().trim()
            password = binding.password.text.toString().trim()

            if (userName.isBlank() || email.isBlank() ||password.isBlank())
            {
                Toast.makeText(this,"Please fill the details", Toast.LENGTH_SHORT).show()
            }else {
                createAccount(email,password)
            }

        }

    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{task ->
            if(task.isSuccessful)
            {
                Toast.makeText(this,"Account Created Successfully", Toast.LENGTH_SHORT).show()
                saveUserData()
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            else
            {
                Toast.makeText(this,"Account Creation Failed", Toast.LENGTH_SHORT).show()
                Log.d("Account","createAccount",task.exception)
            }
        }


    }

    // save data into database
    private fun saveUserData() {
        userName = binding.name.text.toString().trim()
        email = binding.email.text.toString().trim()
        password = binding.password.text.toString().trim()

        val user = UserModel(userName,email,password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        // save user data Firebase Database
        database.child("user").child(userId).setValue(user)


    }
}