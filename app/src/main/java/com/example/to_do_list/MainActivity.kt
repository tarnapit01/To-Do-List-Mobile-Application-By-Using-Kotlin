package com.example.to_do_list

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_list.adapter.Todo_Adapter
import com.example.to_do_list.database_model.ToDo_Model
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private var btnAdd: Button? = null
    private lateinit var showTask: RecyclerView
    private lateinit var adapter: Todo_Adapter
    private lateinit var database: DatabaseReference
    private val taskList = mutableListOf<ToDo_Model>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        init()
        // Add button listener
        btnAdd?.setOnClickListener {
            val intent = Intent(this, Add_page::class.java)
            startActivity(intent)
        }

        database = FirebaseDatabase.getInstance().getReference("AddTask")
        showTask = findViewById(R.id.Show_task)
        showTask.layoutManager = LinearLayoutManager(this)
        adapter = Todo_Adapter(taskList)
        showTask.adapter = adapter

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                taskList.clear()
                for (data in snapshot.children) {
                    val task = data.getValue(ToDo_Model::class.java)
                    if (task != null) {
                        taskList.add(task)
                    }
                }
                adapter.notifyDataSetChanged() // แจ้ง RecyclerView ว่ามีการเปลี่ยนแปลงข้อมูล
            }

            override fun onCancelled(error: DatabaseError) {
                // จัดการข้อผิดพลาด
            }
        })

    }

    private fun init() {
        btnAdd = findViewById(R.id.btnAddHome)

    }



}
