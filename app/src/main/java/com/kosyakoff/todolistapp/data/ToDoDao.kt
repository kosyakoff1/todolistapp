package com.kosyakoff.todolistapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kosyakoff.todolistapp.data.models.ToDoData

@Dao
interface ToDoDao {

    @Query("select * from todo_table order by id asc")
    fun getAllData(): LiveData<List<ToDoData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoData: ToDoData)

    @Update
    suspend fun updateData(toDoData: ToDoData)

    @Delete
    suspend fun deleteData(toDoData: ToDoData)

    @Query("delete from todo_table")
    suspend fun deleteAll()

    @Query("select * from todo_table where title like '%'||:query||'%' ")
    fun searchDatabase(query: String): LiveData<List<ToDoData>>
}