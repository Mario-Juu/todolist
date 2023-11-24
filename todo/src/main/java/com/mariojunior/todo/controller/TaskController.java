package com.mariojunior.todo.controller;

import com.mariojunior.todo.domain.Task;
import com.mariojunior.todo.service.exception.DataBindingViolationException;
import com.mariojunior.todo.service.exception.ResourceNotFoundException;
import com.mariojunior.todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserController userController;

    @GetMapping("/")
    public List<Task> findAll(){
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Task> findById(@PathVariable Long id){
        try{
            return taskService.findById(id);
        } catch(ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task não encontrada", e);
        }
    }

    @GetMapping("/users/{userId}")
    public List<Task> findAllByUser(){
        return this.taskService.findAllByUser();
    }
    @PostMapping("/")
    public Optional<Task> addTask(@RequestBody Task task){
        return Optional.of(taskService.addTask(task));
    }

    @DeleteMapping("/{id}")
    public Optional<Task> deleteById(@PathVariable Long id){
        try{
            return taskService.deleteById(id);
        } catch (ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task não encontrada", e);
        } catch(DataBindingViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Não foi possível deletar a task pois há um usuário associado.", e);
        }
    }

    @PutMapping("/")
    public Task editUser(@RequestBody Task task){
        return taskService.updateTask(task);
    }
}
