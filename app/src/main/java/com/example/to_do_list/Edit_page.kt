package com.example.to_do_list

import android.content.Intent
import android.os.Bundle
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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Edit_page : AppCompatActivity() {
    private lateinit var editTaskName: EditText
    private lateinit var editTaskTime: EditText
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    private lateinit var btnBack : Button
    private lateinit var database: DatabaseReference
    private lateinit var btnDone : Button
    private lateinit var btnCategory_edit : Spinner
    private var taskId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_page)

        // เชื่อมโยง View
        init()

        // ดึงข้อมูลจาก Intent
        taskId = intent.getStringExtra("taskId")
        val taskName = intent.getStringExtra("taskName")
        val taskTime = intent.getStringExtra("taskTime")
//        val category = intent.getStringExtra("Category")

        // แสดงข้อมูลใน EditText
        editTaskName.setText(taskName)
        editTaskTime.setText(taskTime)


        // อ้างอิงฐานข้อมูล Firebase
        database = FirebaseDatabase.getInstance().getReference("AddTask")

        btnBack.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // ปุ่มอัปเดตข้อมูล
        btnUpdate.setOnClickListener {
            updateTask()
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // ปุ่มลบข้อมูล
        btnDelete.setOnClickListener {
            deleteTask()
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnDone.setOnClickListener {
            deleteTask()
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Setting up Spinner with categories
        val categories = arrayOf("All", "Work", "Personal") // Categories to choose from
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)
        btnCategory_edit.adapter = arrayAdapter

    }
    // ฟังชันก์เชื่อมโยง View
    fun init(){
        // เชื่อมโยง View
        editTaskName = findViewById(R.id.edtEditTask)
        editTaskTime = findViewById(R.id.Edit_Time_Edit)
        btnUpdate = findViewById(R.id.btnSaveEdit)
        btnDelete = findViewById(R.id.btnDaleteEdit)
        btnBack = findViewById(R.id.btnBackEdit)
        btnDone = findViewById(R.id.btnDone)
        btnCategory_edit = findViewById(R.id.btnCategory_edit)
    }

    private fun updateTask() {
        val updatedName = editTaskName.text.toString().trim()
        val updatedTime = editTaskTime.text.toString().trim()
        val updateCategory = btnCategory_edit.selectedItem.toString().trim()
        if (taskId != null && updatedName.isNotEmpty() && updatedTime.isNotEmpty() && updateCategory.isNotEmpty()) {
            val updatedTask = ToDo_Model(taskId!!, updatedName, updatedTime,updateCategory)
            database.child(taskId!!).setValue(updatedTask)
                .addOnSuccessListener {
                    Toast.makeText(this, "Task updated successfully!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to update task.", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteTask() {
        if (taskId != null) {
            database.child(taskId!!).removeValue()
                .addOnSuccessListener {
                    Toast.makeText(this, "Task deleted successfully!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to delete task.", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Task ID is null, cannot delete.", Toast.LENGTH_SHORT).show()
        }
    }


}
