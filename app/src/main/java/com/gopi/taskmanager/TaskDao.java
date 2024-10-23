package com.gopi.taskmanager;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT * FROM task_table ORDER BY due_date")
    LiveData<List<Task>> getAllTasks();

    @Query("SELECT * FROM task_table WHERE id = :taskId")
    LiveData<Task> getTaskById(int taskId);
}
