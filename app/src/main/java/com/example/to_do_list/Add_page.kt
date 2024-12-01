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
import com.example.to_do_list.database_model.AddTask
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
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }




        btnAdd!!.setOnClickListener {
            val taskDescription = edtTask?.text.toString()
            val time = edtTime?.text.toString()
            if (taskDescription.isNotEmpty()){
                createTask(taskDescription,time)
            }else{
                edtTask!!.setError("Task Can't be Empty")
            }
        }


    }

    // Create (Add) a task to Firebase
    private fun createTask(description: String,time:String) {
        val taskId = database?.push()?.key // Generate unique key
        val task = AddTask(taskId,description,time)

        taskId?.let {
            database!!.child(it).setValue(task).addOnSuccessListener {
                Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show()
                edtTask?.text?.clear()
            }.addOnFailureListener { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun init(){
        btnBack = findViewById(R.id.btnBackAdd)
        edtTask = findViewById(R.id.edtAddTask)
        btnAdd = findViewById(R.id.btnSaveAdd)
        edtTime = findViewById(R.id.Edit_Time_Add)
    }
    }