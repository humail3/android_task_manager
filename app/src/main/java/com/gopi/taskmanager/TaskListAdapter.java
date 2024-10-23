package com.gopi.taskmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {
    private List<Task> taskList = new ArrayList<>();
    private OnTaskClickListener onTaskClickListener;
    private OnTaskDeleteClickListener onTaskDeleteClickListener;
    private OnTaskViewClickListener onTaskViewClickListener;

    public interface OnTaskClickListener {
        void onEditTaskClick(Task task);
    }

    public interface OnTaskDeleteClickListener {
        void onDeleteTaskClick(Task task);
    }

    public interface OnTaskViewClickListener {
        void onViewTaskClick(Task task);
    }

    public void setOnTaskClickListener(OnTaskClickListener listener) {
        this.onTaskClickListener = listener;
    }

    public void setOnTaskDeleteClickListener(OnTaskDeleteClickListener listener) {
        this.onTaskDeleteClickListener = listener;
    }

    public void setOnTaskViewClickListener(OnTaskViewClickListener listener) {
        this.onTaskViewClickListener = listener;
    }

    public void setTaskList(List<Task> tasks) {
        this.taskList = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task currentTask = taskList.get(position);
        holder.titleTextView.setText(currentTask.getTitle());
        holder.descriptionTextView.setText(currentTask.getDescription());
        holder.dueDateTextView.setText(currentTask.getDueDate());

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTaskDeleteClickListener != null) {
                    onTaskDeleteClickListener.onDeleteTaskClick(currentTask);
                }
            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTaskClickListener != null) {
                    onTaskClickListener.onEditTaskClick(currentTask);
                }
            }
        });

        holder.viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTaskViewClickListener != null) {
                    onTaskViewClickListener.onViewTaskClick(currentTask);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView descriptionTextView;
        private TextView dueDateTextView;
        private Button deleteButton;
        private Button editButton;
        private Button viewButton;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            dueDateTextView = itemView.findViewById(R.id.dueDateTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton = itemView.findViewById(R.id.editButton);
            viewButton = itemView.findViewById(R.id.viewButton);
        }
    }
}
