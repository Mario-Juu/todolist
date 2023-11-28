package com.mariojunior.todo.service;

import com.mariojunior.todo.domain.Task;
import com.mariojunior.todo.domain.User;
import com.mariojunior.todo.domain.enums.ProfileEnums;
import com.mariojunior.todo.security.UserSpringSecurity;
import com.mariojunior.todo.service.exception.AuthorizationException;
import com.mariojunior.todo.service.exception.DataBindingViolationException;
import com.mariojunior.todo.service.exception.ResourceNotFoundException;
import com.mariojunior.todo.repository.TaskRepository;
import com.mariojunior.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private UserService userService;

    public List<Task> findAll(){
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if(!userSpringSecurity.hasRole(ProfileEnums.ADMIN))
            return taskRepository.findAllById(userSpringSecurity.getId());
        return taskRepository.findAll();
    }

    public Task addTask(Task task){
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if(Objects.isNull(userSpringSecurity))
            throw new AuthorizationException("Acesso negado!");
        Optional<User> user = userService.findById(userSpringSecurity.getId());
        task.setId(null);
        task.setUser(user.get());
        return taskRepository.save(task);
    }

    public Task updateTask(Task task){
        Task newTask = findById(task.getId()).get();
        newTask.setDescription(task.getDescription());
        return taskRepository.save(newTask);
    }

    public Optional<Task> findById(Long id){
        Optional<Task> task = taskRepository.findById(id);
        if(task.isEmpty()){
            throw new ResourceNotFoundException();
        }
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if(Objects.isNull(userSpringSecurity) || !userSpringSecurity.hasRole(ProfileEnums.ADMIN) && !userHasTask(userSpringSecurity, task.get()))
            throw new AuthorizationException("Acesso negado!");
        return task;
    }

    public Optional<Task> deleteById(Long id){
        Optional<Task> deletedTask = findById(id);
        if(deletedTask.isEmpty()){
            throw new ResourceNotFoundException();
        }
        try{
            taskRepository.delete(deletedTask.get());
        } catch (Exception e){
            throw new DataBindingViolationException("Não foi possível deletar o usuário pois há uma tarefa associada.");
        }
        return deletedTask;
    }

    public boolean userHasTask(UserSpringSecurity userSpringSecurity, Task task){
        return userSpringSecurity.getId().equals(task.getUser().getId());
    }


}
