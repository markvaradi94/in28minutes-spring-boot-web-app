package com.in28minutes.springboot.springbootwebapp.todo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.security.Principal;

import static java.util.Optional.ofNullable;
import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@SessionAttributes("name")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @RequestMapping("list-todos")
    public String getTodosForUser(ModelMap model) {
        String username = getLoggedInUsername();
        model.put("todos", todoService.findByUsername(username));
        return "listTodos";
    }

    @RequestMapping(value = "add-todo", method = GET)
    public String showNewTodosPage(ModelMap model) {
        model.put("todo", new Todo());
        return "todo";
    }

    @RequestMapping(value = "add-todo", method = POST)
    public String addNewTodo(@Valid Todo todo, BindingResult result) {
        if (result.hasErrors()) {
            return "todo";
        }

        String username = getLoggedInUsername();
        todoService.addTodo(username, todo);
        return "redirect:list-todos";
    }

    @RequestMapping(value = "delete-todo")
    public String deleteTodo(@RequestParam int id) {
        todoService.deleteById(id);
        return "redirect:list-todos";
    }

    @RequestMapping(value = "update-todo")
    public String showUpdateTodo(@RequestParam int id, ModelMap model) {
        Todo todo = todoService.findById(id);
        model.addAttribute("todo", todo);
        return "todo";
    }

    @RequestMapping(value = "update-todo", method = POST)
    public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
        if (result.hasErrors()) {
            return "todo";
        }

        String username = getLoggedInUsername();
        todo.setUsername(username);
        todoService.updateTodo(todo);
        model.addAttribute("todo", todo);
        return "redirect:list-todos";
    }

    private String getLoggedInUsername() {
        return ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Principal::getName)
                .orElse(EMPTY);
    }
}
