package com.example.notesapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.NotesRepository
import com.example.notesapp.data.entity.Notes
import com.example.notesapp.data.room.NotesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application): AndroidViewModel(application) {
    private val notesDao = NotesDatabase.getDatabase(application).notesDao()
    private val repository:NotesRepository = NotesRepository(notesDao)

    //funsi ini yang akang di gunakan oleh view ketika input data dan dikirimkan ke dalam repository
    fun insertData(notes: Notes){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertNotes(notes)
        }
    }
}