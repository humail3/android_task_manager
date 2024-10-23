package com.gopi.taskmanager;
import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskRepository {

    private TaskDao taskDao;
    private LiveData<List<Task>> allTasks;

    public TaskRepository(Application application) {
        TaskDatabase database = TaskDatabase.getInstance(application);
        taskDao = database.taskDao();
        allTasks = taskDao.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public void insert(Task task) {
        TaskDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.insert(task);
        });
    }

    public void update(Task task) {
        TaskDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.update(task);
        });
    }

    public void delete(Task task) {
        TaskDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.delete(task);
        });
    }

    public LiveData<Task> getTaskById(int taskId) {
        return taskDao.getTaskById(taskId);
    }
}
