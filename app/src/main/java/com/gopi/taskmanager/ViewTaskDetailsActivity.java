package com.gopi.taskmanager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class ViewTaskDetailsActivity extends AppCompatActivity {
    private TextView textViewTitle;
    private TextView textViewDescription;
    private TextView textViewDueDate;
    private TaskViewModel taskViewModel;
    private int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task_details);

        textViewTitle = findViewById(R.id.textViewTitle);
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewDueDate = findViewById(R.id.textViewDueDate);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        taskId = getIntent().getIntExtra("task_id", -1);
        if (taskId == -1) {
            // Handle error: Task ID not provided
            finish();
        }

        taskViewModel.getTaskById(taskId).observe(this, new Observer<Task>() {
            @Override
            public void onChanged(Task task) {
                if (task != null) {
                    textViewTitle.setText(task.getTitle());
                    textViewDescription.setText(task.getDescription());
                    textViewDueDate.setText(task.getDueDate());
                } else {
                    // Handle error: Task not found
                    finish();
                }
            }
        });
    }
}
