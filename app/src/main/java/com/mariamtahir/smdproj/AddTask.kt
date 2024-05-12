package com.mariamtahir.smdproj

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class AddTask : AppCompatActivity() {
    //webservices declaration
    //val ROOT_URL: String = "http://192.168.18.14/zenagenda/v1/addtask.php"
    val ADDTASK_URL:String="http://192.168.18.14/zenagenda/v1/addtask.php"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        // getting the layout variables for input
        val taskname=findViewById<EditText>(R.id.task_txt)
        //these are to be implemented in the end if we have time
        val deadline=findViewById<EditText>(R.id.dline_txt)
        val remindme=findViewById<EditText>(R.id.remind_txt)
    //--------------------------------------------------------------------------
        //getting button views
        val add_btn=findViewById<Button>(R.id.add_btn)


        //setting the onclick listener for adding a task
        add_btn.setOnClickListener {

            val taskName = taskname.text.toString()
            val userid="Lt65v5T76iXmbz7YFkj0avhsdp32" //static id for now REPLACE WITH GETTER

            //using Volley to make a POST request
            val stringRequest = object : StringRequest(Method.POST, ADDTASK_URL,
                Response.Listener { response -> //success handler :)
                    Log.d("AddTask", "Response: $response")
                    Toast.makeText(this@AddTask, "Task added successfully", Toast.LENGTH_LONG).show()
                },
                Response.ErrorListener { error -> //error handling
                    Log.e("AddTask", "Error: ${error.message}", error)
                    Toast.makeText(this@AddTask, "Error adding task", Toast.LENGTH_SHORT).show()
                }) {


     //-------------------------------------------------------------------------------------
                //parameter getter
                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()
                    params["userid"] = userid
                    params["task_name"] = taskName
                    return params
                }
    //-----------------------------------------------------------------------------------------

            }
            //adding the POST request to the request queue
            VolleyLog.DEBUG = true
            Volley.newRequestQueue(this@AddTask).add(stringRequest)
        }



    }
}