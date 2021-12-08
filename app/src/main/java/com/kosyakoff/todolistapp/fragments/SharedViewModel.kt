package com.kosyakoff.todolistapp.fragments

import android.app.Application
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.kosyakoff.todolistapp.R
import com.kosyakoff.todolistapp.data.models.Priority

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    val listener: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when (position) {
                0 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(
                        application, R.color.red))
                }
                1 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(
                        application, R.color.yellow))
                }
                2 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(
                        application, R.color.green))
                }
            }
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
        }

    }

    fun parsePriorityString(priorityString: String): Priority {
        val index = getApplication<Application>().resources.getStringArray(R.array.priorities)
            .indexOfFirst { x -> x == priorityString }
        return Priority.values()[index]
    }

    fun verifyDataFromUser(title: String, description: String): Boolean =
        title.isNotEmpty() && description.isNotEmpty()
}