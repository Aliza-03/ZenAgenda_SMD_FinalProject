package com.mariamtahir.smdproj

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
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
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class taskslist() : AppCompatActivity(), Parcelable {


    //recycler view declarations + Mutable List required for it
    private lateinit var recyclerView: RecyclerView
    private lateinit var todo_task_list: MutableList<String>
    //get current user
    private lateinit var auth: FirebaseAuth


    //------------------------------------------------------------------
    lateinit var task: MutableList<String> //not sure if I need it

    constructor(parcel: Parcel) : this() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tasks_list)
        //user auth
        auth = FirebaseAuth.getInstance()
        val user=auth.currentUser?.uid

        todo_task_list = mutableListOf()
        //val editbutton: ImageButton = findViewById(R.id.editbutton)
        val addtask = findViewById<ImageView>(R.id.add_task_btn)

        //recycler view code for tasks that are to be done
        recyclerView = findViewById<RecyclerView>(R.id.to_do_rv)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        //todo_rv.layoutManager=LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)


        //VOLLEY CODE
        val userId = user //getting the user id
        val status = false

       // Toast.makeText(this,"$userId",Toast.LENGTH_LONG).show()
        val tlist= mutableListOf<String>()

        if (userId != null) {
            Log.d("MainActivity", "userId: $userId, status: $status")
            getTasks(this, userId, status) { retrievedTasks, error ->
                if (error != null) {
                    Log.e("MainActivity", "GET failed. Error: $error")
                    Toast.makeText(this, "GET failed $retrievedTasks", Toast.LENGTH_LONG).show()
                } else {
                    // Assign the retrieved tasks to todo_task_list
                    retrievedTasks?.let {
                        todo_task_list.clear()
                        todo_task_list.addAll(it)
                        // Update UI with the todo_task_list
                        recyclerView.adapter?.notifyDataSetChanged()
                    }
                }
            }
        }

        //--------------------------------------------------------------------------------------

         /* todo_task_list = mutableListOf( //dummy data
            ToDo_Data( "Task 1"),
            ToDo_Data( "Task 2"),
            ToDo_Data( "Task 3"),
            ToDo_Data( "Task 4"),
            ToDo_Data( "Task 5")
        )
*/
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

    fun getTasks(
        context: Context,
        userId: String,
        status: Boolean,
        callback: (MutableList<String>?, String?) -> Unit
    ) {
        //url for webservices - view tasks

        val url = "http://192.168.18.14/zenagenda/v1/gettask.php"//?userid=Lt65v5T76iXmbz7YFkj0avhsdp32&status=false"

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.GET, url, null,
                    Response.Listener<JSONObject> { response ->
                        Log.d("getTasks", "Response received: $response")
                        val error = response.getBoolean("error")
                        val message = response.getString("message")
                        if (!error) {
                            // Tasks received successfully
                            val tasksArray = response.getJSONArray("tasks")
                            val taskList = mutableListOf<String>()
                            for (i in 0 until tasksArray.length()) {
                                taskList.add(tasksArray.getString(i))
                            }
                            callback(taskList, null)
                        } else {
                            // Error occurred
                            callback(null, message)
                        }
                    },
                    Response.ErrorListener { error ->
                        Log.e("getTasks", "Error: ${error.message}")
                        callback(null, error.message)
                    })

                Volley.newRequestQueue(context).add(jsonObjectRequest)
            } catch (e: Exception) {
                callback(null, e.message)
            }
        }
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


            //function to get the to do data of tasks that are yet to be done

            fun <ToDo_Data> MutableList<ToDo_Data>.addAll(elements: MutableList<ToDo_Data>) {
                this.addAll(elements)
            }
        }


