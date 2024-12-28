package com.example.to_do_list.adapter


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_list.Edit_page
import com.example.to_do_list.R
import com.example.to_do_list.database_model.ToDo_Model


class Todo_Adapter (private val itemList: List<ToDo_Model>,  private val onItemClick: (ToDo_Model) -> Unit
) : RecyclerView.Adapter<Todo_Adapter.TodoViewHolder>(){



    class TodoViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView){
        val taskView : TextView = itemView.findViewById(R.id.txtTask)
        val time : TextView = itemView.findViewById(R.id.textTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.info_layout, parent, false)
        return TodoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val task = itemList[position]
        holder.taskView.text = task.task
        holder.time.text = buildString {
            append(task.Time)
            append(" minute")
        }
        holder.itemView.setOnClickListener{
            onItemClick(task)
        }


    }

}