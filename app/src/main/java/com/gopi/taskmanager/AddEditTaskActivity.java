package com.gopi.taskmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class AddEditTaskActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextDueDate;
    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextDueDate = findViewById(R.id.editTextDueDate);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        Button buttonSaveTask = findViewById(R.id.buttonSaveTask);
        buttonSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    saveTask();
                }
            }
        });

        // Retrieve task ID if editing an existing task
        int taskId = getIntent().getIntExtra("task_id", -1);
        if (taskId != -1) {
            // Load existing task data for editing
            loadTaskData(taskId);
        }
    }

    private void loadTaskData(int taskId) {
        // Fetch task data from the database based on task ID
        taskViewModel.getTaskById(taskId).observe(this, task -> {
            if (task != null) {
                // Populate EditText fields with task data
                editTextTitle.setText(task.getTitle());
                editTextDescription.setText(task.getDescription());
                editTextDueDate.setText(task.getDueDate());
            }
        });
    }

    private boolean validateInput() {
        String title = editTextTitle.getText().toString().trim();
        String dueDate = editTextDueDate.getText().toString().trim();

        if (title.isEmpty()) {
            editTextTitle.setError("Task title is required");
            return false;
        }

        if (!isValidDate(dueDate)) {
            editTextDueDate.setError("Invalid due date format (DD-MM-YYYY)");
            return false;
        }

        return true;
    }

    private boolean isValidDate(String date) {
        // Check if the date is in the correct format (YYYY-MM-DD)
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        dateFormat.setLenient(false); // Disable lenient parsing
        try {
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private void saveTask() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String dueDate = editTextDueDate.getText().toString().trim();

        int taskId = getIntent().getIntExtra("task_id", -1);
        if (taskId == -1) {
            // Adding a new task
            Task task = new Task(title, description, dueDate);
            taskViewModel.insert(task);
        } else {
            // Updating an existing task
            Task updatedTask = new Task(title, description, dueDate);
            updatedTask.setId(taskId); // Set the ID of the existing task
            taskViewModel.update(updatedTask);
        }

        finish(); // Close activity after saving
    }

}
