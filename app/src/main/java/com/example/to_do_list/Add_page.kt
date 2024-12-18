package com.example.to_do_list

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import com.example.to_do_list.database_model.ToDo_Model
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Add_page : AppCompatActivity() {
    private var btnBack : Button? = null
    private var edtTask : EditText? = null
    private var btnAdd : Button? = null
    private var database : DatabaseReference? = null
    private var edtTime : EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_page)

        database = FirebaseDatabase.getInstance().reference.child("AddTask")
        init()
        btnBack!!.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnAdd!!.setOnClickListener {
            insertTask()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }






    }
    // Create (Add) a task to Firebase
    private fun insertTask(){
        val taskDes = edtTask!!.text.toString()
        val time = edtTime!!.text.toString()

        if (taskDes.isEmpty()){
            edtTask!!.error = ("Please input task")
        }
        if(time.isEmpty()){
            edtTime!!.error = ("Please input time")
        }

        val taskId = database?.push()?.key
        val task = ToDo_Model(id = taskId,task = taskDes,Time = time)

        taskId?.let { database!!.child(it).setValue(task)
            .addOnCompleteListener{
                edtTask!!.text.clear()
                edtTime!!.text.clear()
            }
        }

    }

    private fun init(){
        btnBack = findViewById(R.id.btnBackAdd)
        edtTask = findViewById(R.id.edtAddTask)
        btnAdd = findViewById(R.id.btnSaveAdd)
        edtTime = findViewById(R.id.Edit_Time_Add)
    }
    }