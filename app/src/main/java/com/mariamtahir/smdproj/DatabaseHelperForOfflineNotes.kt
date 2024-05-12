package com.mariamtahir.smdproj

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.widget.Toast

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    companion object {
        private const val DATABASE_NAME = "notes.db"
        private const val TABLE_NAME = "notes"
        private const val COL_ID = "id"
        private const val COL_TITLE = "title"
        private const val COL_CONTENT = "content"
        private const val COL_IMAGE_URI = "image_uri"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_TITLE TEXT, $COL_CONTENT TEXT, $COL_IMAGE_URI TEXT)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun saveNoteToSQLite(title: String, content: String, imageUri: Uri?, context: Context) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COL_TITLE, title)
            put(COL_CONTENT, content)
            imageUri?.let {
                put(COL_IMAGE_URI, it.toString())
            }
        }
        val result = db.insert(TABLE_NAME, null, contentValues)
        if (result == -1L) {
            Toast.makeText(
                context,
                "Failed to save note to local storage (SQLite)!",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                context,
                "Note saved to local storage (SQLite) for guest user!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun getAllNotes(context: Context): List<Note> {
        val notesList = mutableListOf<Note>()
        val db = this.readableDatabase
        val cursor: Cursor? = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        cursor?.use {
            val titleIndex = it.getColumnIndex(COL_TITLE)
            val contentIndex = it.getColumnIndex(COL_CONTENT)
            val imageUriIndex = it.getColumnIndex(COL_IMAGE_URI)
            if (titleIndex == -1 || contentIndex == -1 || imageUriIndex == -1) {
                // Handle error: One or more column indices not found
                return notesList
            }
            if (it.moveToFirst()) {
                do {
                    val title = it.getString(titleIndex)
                    val content = it.getString(contentIndex)
                    val imageUriString = it.getString(imageUriIndex)
                    val imageUri = Uri.parse(imageUriString)
                    val note = Note(title, content, imageUri)
                    notesList.add(note)
                } while (it.moveToNext())
            }
        }
        return notesList
    }

    fun saveImageUriToSQLite(imageUri: String, noteTitle: String, context: Context) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_TITLE, noteTitle)
            put(COL_IMAGE_URI, imageUri)
        }
        val newRowId = db.insert(TABLE_NAME, null, values)
        if (newRowId == -1L) {
            Toast.makeText(context, "Error: Image URI not saved to SQLite database", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Image URI saved to SQLite database", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }
}
