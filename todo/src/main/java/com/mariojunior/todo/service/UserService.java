package com.mariojunior.todo.service;

import com.mariojunior.todo.domain.User;
import com.mariojunior.todo.domain.enums.ProfileEnums;
import com.mariojunior.todo.security.UserSpringSecurity;
import com.mariojunior.todo.service.exception.AuthorizationException;
import com.mariojunior.todo.service.exception.DataBindingViolationException;
import com.mariojunior.todo.service.exception.ResourceNotFoundException;
import com.mariojunior.todo.repository.TaskRepository;
import com.mariojunior.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
   private UserRepository userRepository;
    @Autowired
   private TaskRepository taskRepository;

    public List<User> findAll(){
        UserSpringSecurity userSpringSecurity = authenticated();
        if(!userSpringSecurity.hasRole(ProfileEnums.ADMIN))
            throw new AuthorizationException("Acesso negado!");
        return userRepository.findAll();
    }

    public User addUser(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setProfiles(Stream.of(ProfileEnums.USER.getCode()).collect(Collectors.toSet()));
        return userRepository.save(user);
    }

    public User updateUser(User user){
        User updatedUser = findById(user.getId()).get();
        updatedUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(updatedUser);
    }

    public Optional<User> findById(Long id){
        UserSpringSecurity userSpringSecurity = authenticated();
        if(!Objects.nonNull(userSpringSecurity) || !userSpringSecurity.hasRole(ProfileEnums.ADMIN) && !id.equals(userSpringSecurity.getId()))
            throw new AuthorizationException("Acesso negado!");

        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return user;
    }


    public Optional<User> deleteById(Long id){
        Optional<User> deletedUser = findById(id);
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
    public static UserSpringSecurity authenticated(){
        try{
            return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch(Exception e){
            return null;
        }
    }

}
