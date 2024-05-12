package com.mariamtahir.smdproj

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class ProjectSpace_Adapter(private val proj_list:MutableList<Project_data>):
RecyclerView.Adapter<ProjectSpace_Adapter.ProjectViewHolder>(){

    //define colours for alternation
    private val c1=R.color.brown_sugar
    private val c2=R.color.orange
    private val c3=R.color.moss

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val projview= LayoutInflater.from(parent.context).inflate(R.layout.project_card,parent,false)

        return ProjectViewHolder(projview)
    }

    override fun getItemCount(): Int {

        return proj_list.size

    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val currentItem=proj_list[position]

        holder.projname.text=currentItem.projectname
        holder.status_.text=currentItem.status

        //set colour
        val backgroundColor = if (position % 3 == 0) {
            holder.itemView.context.getColor(c1)
        } else if (position%3==1) {
            holder.itemView.context.getColor(c2)
        }
        else
        {
            holder.itemView.context.getColor(c3)
        }
       // holder.itemView.background.setTint(backgroundColor)
        holder.card.setCardBackgroundColor(backgroundColor)
    }


    class ProjectViewHolder(projview: View): RecyclerView.ViewHolder(projview){

        val projname:TextView=projview.findViewById(R.id.projnme)
       // val status_text:TextView=projview.findViewById(R.id.st1)
        val status_:TextView=projview.findViewById(R.id.st2)
        val card: CardView=projview.findViewById(R.id.card)
      //  val editbtn: ImageView =projview.findViewById(R.id.edit)

    }
}