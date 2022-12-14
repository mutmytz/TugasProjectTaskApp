package com.D121201064.TaskApp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.D121201064.TaskApp.model.Task


@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task: Task)

    @Query("SELECT * FROM task WHERE stats='Ongoing'")
    fun readAllData(): LiveData<List<Task>>
    @Query("SELECT * FROM task WHERE stats='Done'")
    fun readAllDataHistory(): LiveData<List<Task>>
    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("DELETE FROM task WHERE stats='Done'")
    suspend fun deleteAllHistory()


}