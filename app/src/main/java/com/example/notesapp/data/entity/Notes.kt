package com.example.notesapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

//anotasi entity untuk menandakan bahwwa sebuah data class di jadikan sebuah table databse

@Entity(tableName = "notes_table")
data class Notes(
    //untuk id di dalam table supaya tidak duplikat
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var priority: Priority,
    var decs: String,
    var date: String
)
