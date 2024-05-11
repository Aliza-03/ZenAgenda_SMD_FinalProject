package com.mariamtahir.smdproj

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class taskslist : AppCompatActivity()  {
    //recycler view declarations + Mutable List required for it
    private lateinit var recyclerView: RecyclerView
    private lateinit var todo_task_list:MutableList<ToDo_Data>
    //------------------------------------------------------------------
    lateinit var  task:MutableList<String> //not sure if I need it

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tasks_list)
        //val editbutton: ImageButton = findViewById(R.id.editbutton)
        val addtask=findViewById<ImageView>(R.id.add_task_btn)

        //recycler view code for tasks that are to be done
        recyclerView= findViewById<RecyclerView>(R.id.to_do_rv)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        //todo_rv.layoutManager=LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        todo_task_list = mutableListOf( //dummy data
            ToDo_Data(1, "Task 1"),
            ToDo_Data(2, "Task 2"),
            ToDo_Data(3, "Task 3"),
            ToDo_Data(4, "Task 4"),
            ToDo_Data(5, "Task 5")
        )

        val todoAdapter = ToDo_Adapter(todo_task_list)
        recyclerView.adapter = todoAdapter

      //  editbutton.setOnClickListener {

        //    val intent = Intent(this, EditTaskActivity::class.java)
        //    startActivity(intent)
        //}

        addtask.setOnClickListener {

            val intent = Intent(this, AddTask::class.java)
            startActivity(intent)
        }

    }

    //function to get the to do data of tasks that are yet to be done

}