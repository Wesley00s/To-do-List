package com.example.todo.list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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

import com.example.todo.list.data.model.Todo;
import com.example.todo.list.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private final List<Todo> todoList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView textViewAddTask;
    private TextView textViewInstruction;
    private ImageView imageViewArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        final FloatingActionButton fab = binding.fab;
        recyclerView = binding.recyclerView;
        textViewAddTask = binding.textViewAddTask;
        textViewInstruction = binding.textViewInstruction;
        imageViewArrow = binding.imageViewArrow;

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TodoAdapter adapter = new TodoAdapter(todoList, todo -> {
            Intent editIntent = new Intent(MainActivity.this, EditTaskActivity.class);
            editIntent.putExtra("taskToEdit", todo);
            editTaskLauncher.launch(editIntent);
        }, todo -> {
            int position = todoList.indexOf(todo);
            if (position != -1) {
                todoList.remove(position);
                Objects.requireNonNull(recyclerView.getAdapter()).notifyItemRemoved(position);
                updateVisibility();
            }
        });

        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            addTaskLauncher.launch(intent);
        });

        updateVisibility();
    }

    private final ActivityResultLauncher<Intent> editTaskLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Todo editedTask = (Todo) result.getData().getSerializableExtra("editedTask");
                    if (editedTask != null) {
                        for (int i = 0; i < todoList.size(); i++) {
                            if (todoList.get(i).getID() == editedTask.getID()) {
                                todoList.set(i, editedTask);
                                Toast.makeText(this, "Tarefa editada com sucesso!", Toast.LENGTH_SHORT).show();
                                Objects.requireNonNull(recyclerView.getAdapter()).notifyItemChanged(i);
                                break;
                            }
                        }
                    }
                }
            }
    );

    private final ActivityResultLauncher<Intent> addTaskLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Todo newTask = (Todo) result.getData().getSerializableExtra("newTask");
                    if (newTask != null) {
                        todoList.add(newTask);
                        Objects.requireNonNull(recyclerView.getAdapter()).notifyItemInserted(todoList.size() - 1);
                        updateVisibility();
                    }
                }
            }
    );

    private void updateVisibility() {
        if (todoList.isEmpty()) {
            textViewAddTask.setVisibility(View.VISIBLE);
            textViewInstruction.setVisibility(View.VISIBLE);
            imageViewArrow.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            textViewAddTask.setVisibility(View.GONE);
            textViewInstruction.setVisibility(View.GONE);
            imageViewArrow.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
