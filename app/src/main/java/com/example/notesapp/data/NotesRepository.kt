package com.example.notesapp.data

import com.example.notesapp.data.entity.Notes
import com.example.notesapp.data.room.NotesDao

class NotesRepository (private val notesDao: NotesDao){
    suspend fun insertNotes(notes: Notes){
        notesDao.insert(notes)
    }
}