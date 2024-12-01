package com.example.to_do_list

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.database.database


class MainActivity : AppCompatActivity() {
    private var btnAdd :Button? = null
    private var recyclerView : RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        btnAdd!!.setOnClickListener {
            var intent = Intent(this, Add_page::class.java )
            startActivity(intent)
            finish()
        }

        }
    fun init(){
        btnAdd = findViewById(R.id.btnAddHome)
        recyclerView = findViewById(R.id.Show_task)
    }
}
