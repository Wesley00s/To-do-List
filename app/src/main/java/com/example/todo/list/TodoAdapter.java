package com.example.todo.list;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.list.data.model.Todo;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private final List<Todo> todoList;
    private final OnItemClickListener listener;
    private final OnItemDeleteListener deleteListener;

    public TodoAdapter(List<Todo> todoList, OnItemClickListener listener, OnItemDeleteListener deleteListener) {
        this.todoList = todoList;
        this.listener = listener;
        this.deleteListener = deleteListener;
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
        holder.todoCompleted.setChecked(todo.isCompleted());

        holder.todoCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
            todo.setCompleted(isChecked);
            String message = isChecked ? "Tarefa marcada como concluída!" : "Tarefa desmarcada!";
            Toast.makeText(holder.itemView.getContext(), message, Toast.LENGTH_SHORT).show();
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.dueDateTextView.setText(todo.getDueDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }

        switch (todo.getPriority()) {
            case HIGH:
                holder.imageViewPriorityHigh.setVisibility(View.VISIBLE);
                holder.imageViewPriorityMedium.setVisibility(View.GONE);
                holder.imageViewPriorityLow.setVisibility(View.GONE);
                break;
            case MEDIUM:
                holder.imageViewPriorityHigh.setVisibility(View.GONE);
                holder.imageViewPriorityMedium.setVisibility(View.VISIBLE);
                holder.imageViewPriorityLow.setVisibility(View.GONE);
                break;
            case LOW:
                holder.imageViewPriorityHigh.setVisibility(View.GONE);
                holder.imageViewPriorityMedium.setVisibility(View.GONE);
                holder.imageViewPriorityLow.setVisibility(View.VISIBLE);
                break;
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(todo));

        holder.buttonDeleteTask.setOnClickListener(v -> showConfirmationDialog(holder.itemView.getContext(), todo));
    }

    private void showConfirmationDialog(Context context, Todo todo) {
        new AlertDialog.Builder(context)
                .setTitle("Confirmação de Exclusão")
                .setMessage("Você tem certeza que deseja excluir esta tarefa?")
                .setPositiveButton("Sim", (dialog, which) -> deleteListener.onDeleteClick(todo))
                .setNegativeButton("Não", null)
                .show();
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    static class TodoViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView taskTextView;
        TextView dueDateTextView;
        ImageView imageViewPriorityHigh;
        ImageView imageViewPriorityMedium;
        ImageView imageViewPriorityLow;
        CheckBox todoCompleted;
        ImageButton buttonDeleteTask;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.todo_name);
            taskTextView = itemView.findViewById(R.id.todo_task);
            dueDateTextView = itemView.findViewById(R.id.todo_due_date);
            imageViewPriorityHigh = itemView.findViewById(R.id.imageViewPriorityHigh);
            imageViewPriorityMedium = itemView.findViewById(R.id.imageViewPriorityMedium);
            imageViewPriorityLow = itemView.findViewById(R.id.imageViewPriorityLow);
            todoCompleted = itemView.findViewById(R.id.todo_completed);
            buttonDeleteTask = itemView.findViewById(R.id.buttonDeleteTask);
        }
    }
}