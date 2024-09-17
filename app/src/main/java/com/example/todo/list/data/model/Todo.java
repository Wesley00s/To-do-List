package com.example.todo.list.data.model;

import com.example.todo.list.data.enumeration.Priority;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Todo implements Serializable {
    private String name;
    private String task;
    private Priority priority;
    private LocalDateTime dueDate;
    private boolean completed;

    public Todo(String name, String task, Priority priority, LocalDateTime dueDate, boolean completed) {
        this.name = name;
        this.task = task;
        this.priority = priority;
        this.dueDate = dueDate;
        this.completed = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
