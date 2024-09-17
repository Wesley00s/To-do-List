package com.example.todo.list;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.list.data.model.Todo;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private final List<Todo> todoList;

    public TodoAdapter(List<Todo> todoList) {
        this.todoList = todoList;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todo, parent, false);
        return new TodoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo todo = todoList.get(position);
        holder.nameTextView.setText(todo.getName());
        holder.taskTextView.setText(todo.getTask());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.dueDateTextView.setText(todo.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    static class TodoViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView taskTextView;
        TextView dueDateTextView;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.todo_name);
            taskTextView = itemView.findViewById(R.id.todo_task);
            dueDateTextView = itemView.findViewById(R.id.todo_due_date);
        }
    }
}
