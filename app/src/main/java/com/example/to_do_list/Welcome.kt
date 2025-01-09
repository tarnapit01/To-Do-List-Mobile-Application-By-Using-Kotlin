package com.example.to_do_list

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Welcome : AppCompatActivity() {

    private lateinit var btnLogin : Button
    private lateinit var btnGuess : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_welcome)


        init()
        btnGuess.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }



    }
    fun init(){
        btnGuess = findViewById(R.id.btnGuess)
    }

}