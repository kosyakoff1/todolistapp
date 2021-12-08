package com.kosyakoff.todolistapp.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kosyakoff.todolistapp.R
import com.kosyakoff.todolistapp.data.models.Priority

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    fun parsePriorityString(priorityString: String): Priority {
        val index = getApplication<Application>().resources.getStringArray(R.array.priorities)
            .indexOfFirst { x -> x == priorityString }
        return Priority.values()[index]
    }

    fun verifyDataFromUser(title: String, description: String): Boolean =
        title.isNotEmpty() && description.isNotEmpty()
}