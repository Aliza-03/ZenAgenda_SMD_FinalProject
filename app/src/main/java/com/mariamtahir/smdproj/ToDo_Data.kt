package com.mariamtahir.smdproj

//This class will contain the data of the tasks
data class ToDo_Data(
    val id:Int,
    val task: String,
    val done:Boolean = false
)
