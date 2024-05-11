package com.mariamtahir.smdproj

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ToDo_Adapter(private val todo_tasks:MutableList<ToDo_Data>) :
    RecyclerView.Adapter<ToDo_Adapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val taskview=LayoutInflater.from(parent.context).inflate(R.layout.tasks_list_rview,parent,false)

        return TaskViewHolder(taskview)
    }

    override fun getItemCount(): Int {
        return todo_tasks.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentItem=todo_tasks[position]
       // holder.check_btn.setImageResource(currentItem.)
        holder.task_.text=currentItem.task
    }

    //------------------------------------------------------------------------------------------------------------------
    class TaskViewHolder(taskview:View):RecyclerView.ViewHolder(taskview) {

        val check_btn: ImageButton =taskview.findViewById(R.id.circle1)
        val task_:TextView= taskview.findViewById(R.id.task1)
        val editbtn:ImageButton=taskview.findViewById(R.id.editbutton)

    }


}