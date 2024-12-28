package com.example.to_do_list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_list.adapter.Todo_Adapter
import com.example.to_do_list.database_model.ToDo_Model
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var btnAdd: Button
    private lateinit var showTask: RecyclerView
    private lateinit var adapter: Todo_Adapter
    private lateinit var database: DatabaseReference
    private val taskList = mutableListOf<ToDo_Model>()
    private lateinit var btnAll :Button
    private lateinit var btnWork :Button
    private lateinit var btnPersonal :Button


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
        adapter = Todo_Adapter(taskList) { task ->
            val intent = Intent(this, Edit_page::class.java)
            intent.putExtra("taskId", task.id)
            intent.putExtra("taskName", task.task)
            intent.putExtra("taskTime", task.Time)
            intent.putExtra("Category",task.Category)
            startActivity(intent)
        }
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
                Log.e("Firebase", "Error: ${error.message}")
            }
        })

        btnAll.setBackgroundColor(resources.getColor(R.color.green))
        // Set up filtering buttons
        btnAll.setOnClickListener {
            filterTasks("All")
            setButtonColor(btnAll,btnWork, btnPersonal)
        }
        btnWork.setOnClickListener {
            filterTasks("Work")
            setButtonColor(btnWork, btnAll, btnPersonal)
        }
        btnPersonal.setOnClickListener {
            filterTasks("Personal")
            setButtonColor(btnPersonal,btnAll,btnWork, )
        }

    }

    private fun init() {
        btnAdd = findViewById(R.id.btnAddHome)
        btnAll = findViewById(R.id.btnAllHome)
        btnWork = findViewById(R.id.btnWorkHome)
        btnPersonal = findViewById(R.id.btnPersonHome)
    }

    private fun filterTasks(category: String) {
        val filteredList = if (category == "All") {
            taskList
        } else {
            taskList.filter { it.Category == category }
        }
        adapter.updateList(filteredList)
    }

    private fun setButtonColor(activeButton: Button, vararg otherButtons: Button) {
        // Change the background of the active button
        activeButton.setBackgroundColor(resources.getColor(R.color.green)) // Active color

        // Reset the background of other buttons to default color
        for (button in otherButtons) {
            button.setBackgroundColor(resources.getColor(R.color.black)) // Default color
        }
    }

}
