package com.mariamtahir.smdproj

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mariamtahir.smdproj.Task
import com.mariamtahir.smdproj.R

class TaskAdapter(private var taskList: List<Task>) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentItem = taskList[position]
        holder.titleTextView.text = currentItem.title
       // holder.statusTextView.text = currentItem.status
    }

    override fun getItemCount() = taskList.size

    fun updateTaskList(newTaskList: List<Task>) {
        taskList = newTaskList
        notifyDataSetChanged()
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.task_title)
      //  val statusTextView: TextView = itemView.findViewById(R.id.task_status)
    }
}
