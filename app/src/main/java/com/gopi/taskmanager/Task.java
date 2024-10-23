package com.gopi.taskmanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @NonNull
    @ColumnInfo(name = "due_date")
    private String dueDate;

    // Constructor
    public Task(@NonNull String title, String description, @NonNull String dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(@NonNull String dueDate) {
        this.dueDate = dueDate;
    }
}
