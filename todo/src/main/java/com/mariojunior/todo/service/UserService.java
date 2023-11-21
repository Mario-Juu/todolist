package com.mariojunior.todo.service;

import com.mariojunior.todo.domain.User;
import com.mariojunior.todo.service.exception.DataBindingViolationException;
import com.mariojunior.todo.service.exception.ResourceNotFoundException;
import com.mariojunior.todo.repository.TaskRepository;
import com.mariojunior.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
   private UserRepository userRepository;
    @Autowired
   private TaskRepository taskRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User addUser(User user){
        return userRepository.save(user);
    }

    public User updateUser(User user){
        return userRepository.save(user);
    }

    public Optional<User> findById(Long id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return user;
    }


    public Optional<User> deleteById(Long id){
        Optional<User> deletedUser = userRepository.findById(id);
        if(deletedUser.isEmpty()){
            throw new ResourceNotFoundException();
        }
        try{
        userRepository.delete(deletedUser.get());
        } catch (Exception e){
            throw new DataBindingViolationException("Não foi possível deletar o usuário pois há uma tarefa associada.");
        }
        return deletedUser;
    }

}
