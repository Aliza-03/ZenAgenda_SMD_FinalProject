package com.mariamtahir.smdproj

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter : ListAdapter<Note, NotesAdapter.NoteViewHolder>(NoteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notesrow, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)
        holder.bind(note)

        // Set click listener for each note item
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, NotePageActivity::class.java).apply {
                putExtra("title", note.title)
                putExtra("content", note.content)
                putExtra("imageUri", note.imageUri?.toString()) // Convert Uri to string
            }
            context.startActivity(intent)
        }
    }
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val noteTitle: TextView = itemView.findViewById(R.id.notetitle)
        private val noteContent: TextView = itemView.findViewById(R.id.notecontent)

        fun bind(note: Note) {
            noteTitle.text = note.title
            noteContent.text = note.content
        }
    }


    class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }
}
