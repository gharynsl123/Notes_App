package com.example.notesapp.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

//anotasi entity untuk menandakan bahwwa sebuah data class di jadikan sebuah table databse

@Parcelize
@Entity(tableName = "notes_table")
data class Notes(
    //untuk id di dalam table supaya tidak duplikat
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var priority: Priority,
    var decs: String,
    var date: String
): Parcelable
