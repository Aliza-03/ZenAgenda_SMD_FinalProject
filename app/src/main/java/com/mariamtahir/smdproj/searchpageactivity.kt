package com.mariamtahir.smdproj

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SearchPageActivity : AppCompatActivity() {

    private lateinit var notesRecyclerView: RecyclerView
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.searchpage)

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        // Initialize RecyclerView and its adapter
        notesRecyclerView = findViewById(R.id.searchrv)
        searchAdapter = SearchAdapter()

        // Set RecyclerView layout manager and adapter
        notesRecyclerView.layoutManager = LinearLayoutManager(this)
        notesRecyclerView.adapter = searchAdapter

        // Get the search query passed from the previous activity
        val query = intent.getStringExtra("query")

        if (!query.isNullOrBlank()) {
            // Filter notes based on the search query and display them
            filterNotes(query)
        } else {
            // Handle case where query is null or blank
            Toast.makeText(this@SearchPageActivity, "No search query provided", Toast.LENGTH_SHORT).show()
        }
    }

    private fun filterNotes(query: String) {
        // Query Firestore to filter notes based on the search query
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val userNotesRef = firestore.collection("users").document(userId)
                .collection("notes").whereEqualTo("title", query) // Modify this to suit your filtering needs
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val filteredNotes = mutableListOf<Note>()
                    for (document in querySnapshot.documents) {
                        val title = document.getString("title")
                        val content = document.getString("content")

                        if (title != null && content != null) {
                            // Create Uri from imageUriString if not null
                            val note = Note(title, content, null)
                            filteredNotes.add(note)
                        }
                    }

                    // Update the RecyclerView with the filtered notes
                    searchAdapter.submitList(filteredNotes)
                }
                .addOnFailureListener { e ->
                    // Handle failure
                    Toast.makeText(this@SearchPageActivity, "Failed to retrieve notes: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            // Handle case where currentUser is null (user not authenticated)
            Toast.makeText(this@SearchPageActivity, "User not authenticated", Toast.LENGTH_SHORT).show()
        }
    }
}
