package com.mariamtahir.smdproj

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView

class profileCustomize : AppCompatActivity() {
    private lateinit var changepfp: CircleImageView


    val PICK_IMAGE_REQUEST = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_customize)


        val save = findViewById<Button>(R.id.saveButton)
        changepfp=findViewById<CircleImageView>(R.id.profilePicture)

        save.setOnClickListener {
            val intent = Intent(this, profilePage::class.java)
            startActivity(intent)
        }

        changepfp.setOnClickListener{

            openGallery()

            }
        }


    // Function to open the gallery

    private fun openGallery() {

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)

    }

    // Handle the result of the gallery selection
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri: Uri = data.data!!
            changepfp.setImageURI(imageUri)
            uploadImageToFirebaseStorage(imageUri)
        }
    }


    private fun uploadImageToFirebaseStorage(imageUri: Uri) {
        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child("profile_pictures/${FirebaseAuth.getInstance().currentUser?.uid}")
        imageRef.putFile(imageUri)
            .addOnSuccessListener { taskSnapshot ->
                // Image uploaded successfully
                // Now, save the download URL to Firebase Realtime Database
                saveImageDownloadUrl(taskSnapshot.metadata?.reference?.downloadUrl)
            }
            .addOnFailureListener { exception ->
                // Handle any errors
                Toast.makeText(this, "Image upload failed: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveImageDownloadUrl(downloadUrl: Task<Uri>?) {
        if (downloadUrl != null) {
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            val databaseRef = FirebaseDatabase.getInstance().reference.child("users").child(userId ?: "")

            downloadUrl.addOnSuccessListener { uri ->
                val imageUrl = uri.toString()
                databaseRef.child("profilePictureUrl").setValue(imageUrl)
                    .addOnSuccessListener {
                        Log.d(TAG, "Profile picture URL saved: $imageUrl")
                        Toast.makeText(this, "Profile picture updated successfully", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { exception ->
                        Log.e(TAG, "Failed to update profile picture", exception)
                        Toast.makeText(this, "Failed to update profile picture: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
            }.addOnFailureListener { exception ->
                Log.e(TAG, "Error getting download URL", exception)
                Toast.makeText(this, "Failed to get download URL: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            Log.e(TAG, "Download URL is null")
            Toast.makeText(this, "Download URL is null", Toast.LENGTH_SHORT).show()
        }
    }


}




