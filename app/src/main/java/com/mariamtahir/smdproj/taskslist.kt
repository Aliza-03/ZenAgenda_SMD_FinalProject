package com.mariamtahir.smdproj

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray

class taskslist() : AppCompatActivity(), Parcelable {


    //recycler view declarations + Mutable List required for it
    private lateinit var recyclerView: RecyclerView
    private lateinit var todo_task_list: MutableList<ToDo_Data>

    //------------------------------------------------------------------
    lateinit var task: MutableList<String> //not sure if I need it

    constructor(parcel: Parcel) : this() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tasks_list)
        todo_task_list = mutableListOf()
        //val editbutton: ImageButton = findViewById(R.id.editbutton)
        val addtask = findViewById<ImageView>(R.id.add_task_btn)

        //recycler view code for tasks that are to be done
        recyclerView = findViewById<RecyclerView>(R.id.to_do_rv)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        //todo_rv.layoutManager=LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)


        //VOLLEY CODE
        val userId = "Lt65v5T76iXmbz7YFkj0avhsdp32" //static id for now REPLACE WITH GETTER
        val status = false

       /* getTasks(this, userId, status) { retrievedTasks, error ->
            if (error != null) {
                Toast.makeText(this, "GET failed", Toast.LENGTH_LONG).show()
            } else {
                // Assign the retrieved tasks to todo_task_list
                retrievedTasks?.let {
                    todo_task_list.clear()
                    todo_task_list.addAll(it)
                    // Update UI with the todo_task_list
                    recyclerView.adapter?.notifyDataSetChanged()
                }
            }
        }*/

        //--------------------------------------------------------------------------------------

          todo_task_list = mutableListOf( //dummy data
            ToDo_Data( "Task 1"),
            ToDo_Data( "Task 2"),
            ToDo_Data( "Task 3"),
            ToDo_Data( "Task 4"),
            ToDo_Data( "Task 5")
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


    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<taskslist> {
        override fun createFromParcel(parcel: Parcel): taskslist {
            return taskslist(parcel)
        }

        override fun newArray(size: Int): Array<taskslist?> {
            return arrayOfNulls(size)
        }
    }

    //function to handle GET function for the retrieval of taska
    fun getTasks(
        context: Context,
        userId: String,
        status: Boolean,
        callback: (MutableList<String>?, String?) -> Unit
    ) {
        //url for webservices - view tasks
        val url = "http://192.168.18.14/zenagenda/v1/gettask.php"//?userid=$userId&status=$status"

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val jsonArrayRequest = JsonArrayRequest(
                    Request.Method.GET, url, null,
                    Response.Listener<JSONArray> { response ->
                        val taskList = mutableListOf<String>()
                        for (i in 0 until response.length()) {
                            taskList.add(response.getString(i))
                        }
                        callback(taskList, null)
                    },
                    Response.ErrorListener { error ->
                        callback(null, error.message)
                    })

                Volley.newRequestQueue(context).add(jsonArrayRequest)
            } catch (e: Exception) {
                callback(null, e.message)
            }
        }
    }

            //function to get the to do data of tasks that are yet to be done

            fun <ToDo_Data> MutableList<ToDo_Data>.addAll(elements: MutableList<String>) {
                this.addAll(elements)
            }
        }


