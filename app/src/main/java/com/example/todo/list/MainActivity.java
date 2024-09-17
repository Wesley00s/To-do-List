package com.example.todo.list;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.list.data.enumeration.Priority;
import com.example.todo.list.data.model.Todo;
import com.example.todo.list.databinding.ActivityMainBinding;
import com.example.todo.list.databinding.ItemTodoBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        ItemTodoBinding itemTodoBinding = ItemTodoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(binding.getRoot().getId()), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        final FloatingActionButton fab = binding.fab;
        final RecyclerView recyclerView = binding.recyclerView;
        List<Todo> todoList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TodoAdapter adapter = new TodoAdapter(todoList);
        recyclerView.setAdapter(adapter);

        ActivityResultLauncher<Intent> addTaskLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Todo newTask = (Todo) data.getSerializableExtra("newTask");
                            if (newTask != null) {
                                todoList.add(newTask);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
        );

        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            addTaskLauncher.launch(intent);
        });

    }
}