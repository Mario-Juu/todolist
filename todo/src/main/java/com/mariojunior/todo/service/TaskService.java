package com.mariojunior.todo.service;

import com.mariojunior.todo.domain.Task;
import com.mariojunior.todo.domain.User;
import com.mariojunior.todo.exception.ResourceNotFoundException;
import com.mariojunior.todo.repository.TaskRepository;
import com.mariojunior.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Task> findAll(){
        return taskRepository.findAll();
    }

    public Task addTask(Task task){
        return taskRepository.save(task);
    }

    public Task updateTask(Task task){
        return taskRepository.save(task);
    }

    public Optional<Task> findById(Long id){
        Optional<Task> task = taskRepository.findById(id);
        if(task.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return task;
    }

    public List<Task> findAllByUserId(Long userId){
        return taskRepository.findByUser_Id(userId);
    }

    public Optional<Task> deleteById(Long id){
        Optional<Task> deletedTask = taskRepository.findById(id);
        if(deletedTask.isEmpty()){
            throw new ResourceNotFoundException();
        }
        try{
            taskRepository.delete(deletedTask.get());
        } catch (Exception e){
            throw new RuntimeException("Não foi possível deletar o usuário pois há uma tarefa associada.");
        }
        return deletedTask;
    }


}
