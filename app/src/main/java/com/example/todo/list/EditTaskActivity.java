package com.example.todo.list;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.todo.list.databinding.ActivityEditTaskBinding;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class EditTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityEditTaskBinding binding = ActivityEditTaskBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        final EditText editTextTaskName = binding.editTextEditTaskName;
        final EditText editTextTaskDescription = binding.editTextEditTaskDescription;
        final TextView editTextDueDate = binding.editTextEditDueDate;
        final Button buttonEditTask = binding.buttonEditTask;
        final Button buttonBack = binding.buttonBack;
        final RadioGroup radioGroupPriority = binding.radioGroupPriority;
        final RadioButton radioButtonHighPriority = binding.radioButtonHighPriority;
        final RadioButton radioButtonMediumPriority = binding.radioButtonMediumPriority;
        final RadioButton radioButtonLowPriority = binding.radioButtonLowPriority;


        Todo todo = (Todo) getIntent().getSerializableExtra("taskToEdit");

        if (todo != null) {
            editTextTaskName.setText(todo.getName());
            editTextTaskDescription.setText(todo.getTask());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                editTextDueDate.setText(todo.getDueDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
            radioGroupPriority.check(todo.getPriority() == Priority.HIGH ? radioButtonHighPriority.getId()
                    : todo.getPriority() == Priority.MEDIUM ? radioButtonMediumPriority.getId() :
                    radioButtonLowPriority.getId());
        }

        editTextDueDate.setOnClickListener(view -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (datePicker, selectedYear, selectedMonth, selectedDay) -> {
                        selectedMonth += 1;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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

        buttonEditTask.setOnClickListener(view -> {
            if (todo != null) {
                todo.setName(editTextTaskName.getText().toString());
                todo.setTask(editTextTaskDescription.getText().toString());

                if (editTextTaskName.getText().toString().trim().isEmpty()) {
                    Toast.makeText(this, "O nome da tarefa não pode ser vazio", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (editTextTaskDescription.getText().toString().trim().isEmpty()) {
                    Toast.makeText(this, "A descrição da tarefa não pode ser vazia", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault());
                    todo.setDueDate(LocalDate.parse(editTextDueDate.getText().toString(), formatter));
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

                todo.setPriority(selectedPriority);

                Intent resultIntent = new Intent();
                resultIntent.putExtra("editedTask", todo);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        buttonBack.setOnClickListener(view -> {
            Toast.makeText(this, "Nenhuma alteração foi salva", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
