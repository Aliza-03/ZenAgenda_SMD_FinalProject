package com.mariamtahir.smdproj

import android.net.Uri
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Notes : AppCompatActivity() {
    private lateinit var searchEdittext: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var notesRecyclerView: RecyclerView
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var searchButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        searchEdittext = findViewById<EditText>(R.id.searchnotes)
        searchButton=findViewById<Button>(R.id.searchbutton)

        val chatButton = findViewById<ImageView>(R.id.chat_)
        val home = findViewById<ImageView>(R.id.home_)
        val addNoteButton = findViewById<ImageView>(R.id.addnotebutton)



        addNoteButton.setOnClickListener {
            val intent = Intent(this, NotePageActivity::class.java)
            startActivity(intent)
        }

        chatButton.setOnClickListener {
            val intent = Intent(this, Chats::class.java)
            startActivity(intent)
        }

        home.setOnClickListener {
            val intent = Intent(this, profilePage::class.java)
            startActivity(intent)
        }
        searchButton.setOnClickListener {
            val query = searchEdittext.text.toString()
            if (query.isNotBlank()) {
                navigateToSearchPage(query)
            } else {
                // Handle empty search query
                Toast.makeText(this@Notes, "Please enter a search query", Toast.LENGTH_SHORT).show()
            }
        }

        notesRecyclerView = findViewById(R.id.noteslistrv)
        notesAdapter = NotesAdapter()

        notesRecyclerView.layoutManager = LinearLayoutManager(this)
        notesRecyclerView.adapter = notesAdapter



        // Move loadNotes() call here
        loadNotes()
    }


    private fun loadNotes() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val userNotesRef = firestore.collection("users").document(userId)
                .collection("notes")

            userNotesRef.get()
                .addOnSuccessListener { querySnapshot ->
                    val notesList = mutableListOf<Note>()
                    for (document in querySnapshot.documents) {
                        val title = document.getString("title")
                        val content = document.getString("content")
                        val imageUriString = document.getString("imageUri")

                        if (title != null && content != null && imageUriString != null) {
                            val imageUri = Uri.parse(imageUriString)
                            val note = Note(title, content, imageUri)
                            notesList.add(note)

                            // Display imageUriString in a toast
                            //Toast.makeText(this@Notes, "Image URI here: $imageUriString", Toast.LENGTH_SHORT).show()
                        }
                        else if (title != null && content != null) {
                            // If imageUri is null, create Note instance without it
                            val note = Note(title, content, null)
                            notesList.add(note)
                        }
                    }

                    // Check if the notesList contains all notes
                    if (notesList.size == querySnapshot.documents.size) {
                        // All notes have been successfully retrieved
                        Toast.makeText(this@Notes, "All notes loaded successfully!", Toast.LENGTH_SHORT).show()
                    } else {
                        // Some notes might be missing
                        Toast.makeText(this@Notes, "Some notes might be missing!", Toast.LENGTH_SHORT).show()
                    }

                    // Submit the list of notes to the adapter
                    notesAdapter.submitList(notesList)

                }
                .addOnFailureListener { e ->
                    // Handle failure
                    // Log.e(TAG, "Error fetching user data: $e")
                }
        } else {
            // Handle case where currentUser is null (user not authenticated)
            Log.e(TAG, "Current user is null.")

            // Load notes from SQLite for guest user
            val dbHelper = DatabaseHelper(this)
            val notesList = dbHelper.getAllNotes(this)
            notesAdapter.submitList(notesList)
        }
    }
    private fun navigateToSearchPage(query: String) {
        // Navigate to the SearchPageActivity
        val intent = Intent(this, SearchPageActivity::class.java)
        intent.putExtra("query", query) // Pass the search query to the next activity
        startActivity(intent)
    }

}
