package com.mariamtahir.smdproj

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class NotePageActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var dbHelper: DatabaseHelper
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var imageView: ImageView
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        dbHelper = DatabaseHelper(this)
        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val imageUriString = intent.getStringExtra("imageUri")
       // val imageUri: Uri? = imageUriString?.let { Uri.parse(it) }

        val noteTitleEditText = findViewById<EditText>(R.id.title)
        val noteContentEditText = findViewById<EditText>(R.id.noteet)
        val saveNoteButton = findViewById<ImageButton>(R.id.savenotebutton)
        val addImageButton = findViewById<ImageButton>(R.id.addImageButton)
        imageView = findViewById(R.id.imagecontainer)

        noteTitleEditText.text = Editable.Factory.getInstance().newEditable(title ?: "")
        noteContentEditText.text = Editable.Factory.getInstance().newEditable(content ?: "")
        //selectedImageUri = imageUri
        Glide.with(this)
            .load(imageUriString)
            .into(imageView)


        addImageButton.setOnClickListener {
            val intent = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
            }
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
        }

        saveNoteButton.setOnClickListener {
            val currentUser = auth.currentUser
            val noteTitle = noteTitleEditText.text.toString()
            val noteContent = noteContentEditText.text.toString()

            if (noteTitle.isNotEmpty() && noteContent.isNotEmpty()) {
                currentUser?.let { user ->
                    saveNoteToCloudFirestore(user.uid, noteTitle, noteContent)
                } ?: saveNoteToSQLite(noteTitle, noteContent)
            } else {
                Toast.makeText(
                    this,
                    "Please enter both note title and content",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Load the image using Glide
        Glide.with(this)
            .load(selectedImageUri)
            .into(imageView)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data
            // Reload the image using Glide after selecting it
            Glide.with(this)
                .load(selectedImageUri)
                .into(imageView)
        }
    }

    private fun saveNoteToCloudFirestore(userId: String, title: String, content: String) {
        val userNotesRef = firestore.collection("users").document(userId)
            .collection("notes")

        val note = hashMapOf(
            "title" to title,
            "content" to content,
            "imageUri" to selectedImageUri.toString() // Store image URI as string
        )
        userNotesRef.add(note)
            .addOnSuccessListener {
                Toast.makeText(
                    this,
                    "Note saved successfully to Cloud Firestore!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "Failed to save note to Cloud Firestore: $e",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun saveNoteToSQLite(title: String, content: String) {
        dbHelper.saveNoteToSQLite(title, content, selectedImageUri, this)
    }
}
