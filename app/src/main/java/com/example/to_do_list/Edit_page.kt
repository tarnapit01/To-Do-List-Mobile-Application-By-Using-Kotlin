package com.example.to_do_list

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.FirebaseDatabase

class Edit_page : AppCompatActivity() {
    private lateinit var editTaskName: EditText
    private lateinit var editTaskTime: EditText
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    private lateinit var btnBack : Button
    private lateinit var taskId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_page)

        btnBack.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        init()

        taskId = intent.getStringExtra("taskId") ?: ""


        // Update button functionality
        btnUpdate.setOnClickListener {
            val updatedName = editTaskName.text.toString()
            val updatedTime = editTaskTime.text.toString()
            updateTaskInFirebase(taskId, updatedName, updatedTime)
        }

        // Delete button functionality
        btnDelete.setOnClickListener {
            deleteTaskFromFirebase(taskId)
        }
        }

    private fun init(){
        editTaskTime = findViewById(R.id.Edit_Time_Edit)
        editTaskName = findViewById(R.id.edtEditTask)
        btnUpdate = findViewById(R.id.btnSaveEdit)
        btnDelete = findViewById(R.id.btnDaleteEdit)
        btnBack = findViewById(R.id.btnBackEdit)

    }

    private fun updateTaskInFirebase(taskId: String, name: String, time: String) {
        val databaseRef = FirebaseDatabase.getInstance().getReference("AddTask").child(taskId)
        val taskUpdates = mapOf<String, Any>(
            "Addtask" to name,
            "AddTime" to time
        )

        databaseRef.updateChildren(taskUpdates).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Task updated successfully", Toast.LENGTH_SHORT).show()
                finish() // Go back to MainActivity
            } else {
                Toast.makeText(this, "Failed to update task", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteTaskFromFirebase(taskId: String) {
        val databaseRef = FirebaseDatabase.getInstance().getReference("AddTask").child(taskId)

        databaseRef.removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Task deleted successfully", Toast.LENGTH_SHORT).show()
                finish() // Go back to MainActivity
            } else {
                Toast.makeText(this, "Failed to delete task", Toast.LENGTH_SHORT).show()
            }
        }
    }


}
