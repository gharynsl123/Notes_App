package com.example.notesapp.data.room

import androidx.room.TypeConverter
import com.example.notesapp.data.entity.Priority


class Converter {

    //fungsi ini di [anggil ketika get sebuah database
    @TypeConverter
    fun fromPriority(priority: Priority): String{
        return priority.name
    }

    //ini untuk convert sebuah string kedalam enum class priority
    //funsgsi ini di panggil ketika add dan update sebuah data keedalam database
    @TypeConverter
    fun toPriority(priority: String):Priority{
        return Priority.valueOf(priority)
    }
}