package com.example.todo.list;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.todo.list.data.enumeration.Priority;
import com.example.todo.list.data.model.Todo;
import com.example.todo.list.databinding.ActivityAddTaskBinding;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActivityAddTaskBinding binding = ActivityAddTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        final EditText editTextTaskName = binding.editTextTaskName;
        final EditText editTextTaskDescription = binding.editTextTaskDescription;
        final TextView editTextDueDate = binding.editTextDueDate;
        final Button buttonSaveTask = binding.buttonSaveTask;
        final RadioGroup radioGroupPriority = binding.radioGroupPriority;
        final RadioButton radioButtonHighPriority = binding.radioButtonHighPriority;
        final RadioButton radioButtonMediumPriority = binding.radioButtonMediumPriority;
        final RadioButton radioButtonLowPriority = binding.radioButtonLowPriority;

        editTextDueDate.setOnClickListener(view -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (datePicker, selectedYear, selectedMonth, selectedDay) -> {
                        selectedMonth += 1;

                        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            LocalDate selectedDate = LocalDate.of(selectedYear, selectedMonth, selectedDay);
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            editTextDueDate.setText(selectedDate.format(formatter));
                        } else {
                            String formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", selectedDay, selectedMonth, selectedYear);
                            editTextDueDate.setText(formattedDate);
                        }
                    },
                    year, month, day
            );
            datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

            datePickerDialog.show();
        });

        buttonSaveTask.setOnClickListener(view -> {
            Todo newTask;
            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                String taskName = editTextTaskName.getText().toString();
                String taskDescription = editTextTaskDescription.getText().toString();
                String dueDateString = editTextDueDate.getText().toString();

                if (taskName.trim().isEmpty()) {
                    Toast.makeText(this, "O nome da tarefa não pode ser vazio", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (taskDescription.trim().isEmpty()) {
                    Toast.makeText(this, "A descrição da tarefa não pode ser vazia", Toast.LENGTH_SHORT).show();
                    return;
                }

                LocalDate dueDate;
                try {
                    dueDate = LocalDate.parse(dueDateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                } catch (DateTimeParseException e) {
                    Toast.makeText(this, "Selecione uma data de prazo válida.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int selectedPriorityId = radioGroupPriority.getCheckedRadioButtonId();
                Priority selectedPriority;

                if (selectedPriorityId == radioButtonHighPriority.getId()) {
                    selectedPriority = Priority.HIGH;
                } else if (selectedPriorityId == radioButtonMediumPriority.getId()) {
                    selectedPriority = Priority.MEDIUM;
                } else if (selectedPriorityId == radioButtonLowPriority.getId()) {
                    selectedPriority = Priority.LOW;
                } else {
                    Toast.makeText(this, "Por favor, selecione uma prioridade", Toast.LENGTH_SHORT).show();
                    return;
                }

                newTask = new Todo(taskName, taskDescription, selectedPriority, dueDate, false);

                Intent resultIntent = new Intent(this, MainActivity.class);
                resultIntent.putExtra("newTask", newTask);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}