package com.kosyakoff.todolistapp.data.repository

import androidx.lifecycle.LiveData
import com.kosyakoff.todolistapp.data.ToDoDao
import com.kosyakoff.todolistapp.data.models.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao) {
    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()

    suspend fun insertData(toDoData: ToDoData) {
        toDoDao.insertData(toDoData)
    }
}