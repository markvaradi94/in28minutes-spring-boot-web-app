package com.in28minutes.springboot.springbootwebapp.todo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository repository;

    public List<Todo> findByUsername(String username) {
        return repository.findAllByUsername(username);
    }

    public Todo findById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find todo with ID " + id));
    }

    public void addTodo(String username, Todo newTodo) {
        Todo todoToSave = Todo.builder()
                .username(username)
                .description(newTodo.getDescription())
                .targetDate(newTodo.getTargetDate())
                .done(newTodo.isDone())
                .build();
        repository.save(todoToSave);
    }

    public void deleteById(int id) {
        repository.findById(id)
                .ifPresent(repository::delete);
    }

    public void updateTodo(Todo updatedTodo) {
        Todo todoToUpdate = repository.findById(updatedTodo.getId())
                .orElseThrow(() -> new RuntimeException("Could not find todo with id " + updatedTodo.getId()));
        todoToUpdate.setDescription(updatedTodo.getDescription());
        todoToUpdate.setTargetDate(updatedTodo.getTargetDate());
        todoToUpdate.setDone(updatedTodo.isDone());
        repository.save(todoToUpdate);
    }
}
