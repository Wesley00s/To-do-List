package com.example.todo.list;

import com.example.todo.list.data.model.Todo;

public interface OnItemDeleteListener {
    void onDeleteClick(Todo todo);
}