package com.gopi.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TaskListActivity extends AppCompatActivity implements TaskListAdapter.OnTaskClickListener, TaskListAdapter.OnTaskDeleteClickListener, TaskListAdapter.OnTaskViewClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Enable Edge-to-Edge display
        setContentView(R.layout.activity_task_list);

        // Initialize RecyclerView and adapter
        RecyclerView recyclerView = findViewById(R.id.recyclerViewTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TaskListAdapter adapter = new TaskListAdapter();
        recyclerView.setAdapter(adapter);

        // Initialize TaskViewModel
        TaskViewModel taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        // Observe changes in task list and update RecyclerView
        taskViewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                adapter.setTaskList(tasks);
            }
        });

        // Set click listeners for FloatingActionButton and RecyclerView items
        FloatingActionButton fab = findViewById(R.id.fabAddTask);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start AddEditTaskActivity to add a new task
                Intent intent = new Intent(TaskListActivity.this, AddEditTaskActivity.class);
                startActivity(intent);
            }
        });

        // Set the activity as the click listener for editing tasks
        adapter.setOnTaskClickListener(this);
        // Set the activity as the click listener for deleting tasks
        adapter.setOnTaskDeleteClickListener(this);
        // Set the activity as the click listener for viewing tasks
        adapter.setOnTaskViewClickListener(this);
    }

    // Handle edit task click
    @Override
    public void onEditTaskClick(Task task) {
        // Start AddEditTaskActivity to edit the selected task
        Intent intent = new Intent(TaskListActivity.this, AddEditTaskActivity.class);
        intent.putExtra("task_id", task.getId());
        startActivity(intent);
    }

    // Handle delete task click
    @Override
    public void onDeleteTaskClick(Task task) {
        // Delete the selected task from the database
        TaskViewModel taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.delete(task);
    }

    // Handle view task click
    @Override
    public void onViewTaskClick(Task task) {
        // Start ViewTaskDetailsActivity to view the selected task details
        Intent intent = new Intent(TaskListActivity.this, ViewTaskDetailsActivity.class);
        intent.putExtra("task_id", task.getId());
        startActivity(intent);
    }
}
