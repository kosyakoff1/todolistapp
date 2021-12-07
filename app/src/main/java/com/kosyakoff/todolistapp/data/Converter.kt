package com.kosyakoff.todolistapp.data

import androidx.room.TypeConverter
import com.kosyakoff.todolistapp.data.models.Priority

class Converter {

    @TypeConverter
    fun fromPriority(priority: Priority): String = priority.name

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}