package com.in28minutes.springboot.springbootwebapp.todo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.AUTO;

@Data
@Entity(name = "todos")
@Builder
@AllArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = AUTO)
    private int id;
    private String username;

    @Size(min = 10, message = "Enter at least 10 characters")
    private String description;
    private LocalDate targetDate;
    private boolean done;

    public Todo() {
        this.targetDate = LocalDate.now().plusYears(1);
    }
}
