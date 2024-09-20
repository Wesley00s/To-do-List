package com.example.todo.list.data.model;

import com.example.todo.list.data.enumeration.Priority;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Random;

public class Todo implements Serializable {
    private final int ID;
    private String name;
    private String task;
    private Priority priority;
    private LocalDate dueDate;
    private boolean completed;

    public Todo(String name, String task, Priority priority, LocalDate dueDate, boolean completed) {
        this.ID = new Random().nextInt(Integer.MAX_VALUE);
        this.name = name;
        this.task = task;
        this.priority = priority;
        this.dueDate = dueDate;
        this.completed = completed;
    }

    public int getID() {
        return ID;
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
