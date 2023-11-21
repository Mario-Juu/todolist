package com.mariojunior.todo.controller;

import com.mariojunior.todo.domain.User;
import com.mariojunior.todo.service.exception.DataBindingViolationException;
import com.mariojunior.todo.service.exception.ResourceNotFoundException;
import com.mariojunior.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public List<User> findAll(){
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<User> findById(@PathVariable Long id){
        try{
            return userService.findById(id);
        } catch(ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado", e);
        }
    }
    @PostMapping("/")
    public Optional<User> addUser(@RequestBody User user){
        return Optional.of(userService.addUser(user));
    }

    @DeleteMapping("/{id}")
    public Optional<User> deleteById(@PathVariable Long id){
        try{
            return userService.deleteById(id);
        } catch (ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado", e);
        } catch(DataBindingViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Não foi possível deletar o usuário pois há uma tarefa associada.", e);
        }
    }

    @PutMapping("/")
    public User editUser(@RequestBody User user){
        return userService.updateUser(user);
    }
}
