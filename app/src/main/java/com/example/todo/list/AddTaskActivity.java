package com.example.todo.list;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.list.data.enumeration.Priority;
import com.example.todo.list.data.model.Todo;
import com.example.todo.list.databinding.ActivityAddTaskBinding;
import com.example.todo.list.databinding.ActivityMainBinding;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActivityAddTaskBinding binding = ActivityAddTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(binding.getRoot().getId()), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        final EditText editTextTaskName = binding.editTextTaskName;
        final EditText editTextTaskDescription = binding.editTextTaskDescription;
        final EditText editTextDueDate = binding.editTextDueDate;
        final Button buttonSaveTask = binding.buttonSaveTask;

        editTextDueDate.setOnClickListener(view -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AddTaskActivity.this,
                    (view1, selectedYear, selectedMonth, selectedDay) -> {
                        String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                        editTextDueDate.setText(selectedDate);
                    }, year, month, day);

            datePickerDialog.show();
        });

        buttonSaveTask.setOnClickListener(view -> {
            String taskName = editTextTaskName.getText().toString();
            String taskDescription = editTextTaskDescription.getText().toString();
            String dueDate = editTextDueDate.getText().toString();
            Todo newTask = null;
            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                newTask = new Todo(taskName, taskDescription, Priority.LOW, LocalDateTime.now(), false);
            }

            Intent resultIntent = new Intent(this, MainActivity.class);
            resultIntent.putExtra("newTask", newTask);
            setResult(RESULT_OK, resultIntent);

            finish();
        });
    }
}