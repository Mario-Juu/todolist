package com.mariojunior.todo.controller;

import com.mariojunior.todo.domain.User;
import com.mariojunior.todo.security.UserSpringSecurity;
import com.mariojunior.todo.service.exception.DataBindingViolationException;
import com.mariojunior.todo.service.exception.ResourceNotFoundException;
import com.mariojunior.todo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.mariojunior.todo.service.UserService.authenticated;

@RestController
@RequestMapping(value = "/users", produces = {"application/json"})
@Tag(name = "todoList-api")
public class UserController {

    @Autowired
    private UserService userService;


    @Operation(summary = "Realiza a busca de todos os usuários", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca"),
    })
    @GetMapping("/all")
    public List<User> findAll(){
        return userService.findAll();
    }

    @Operation(summary = "Realiza a busca de um usuário", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca"),
    })
    @GetMapping("/")
    public Optional<User> findById(){
        try{
            UserSpringSecurity userSpringSecurity = authenticated();
            return userService.findById(Objects.requireNonNull(userSpringSecurity).getId());
        } catch(ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado", e);
        }
    }
    @Operation(summary = "Realiza a inserção de novos usuários", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Add realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a add"),
    })

    @PostMapping("/")
    public Optional<User> addUser(@RequestBody User user){
        return Optional.of(userService.addUser(user));
    }

    @Operation(summary = "Deleta um usuário", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o delete"),
    })
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


    @Operation(summary = "Atualiza um usuário", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Att realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a atualização"),
    })
    @PutMapping("/")
    public User editUser(@RequestBody User user){
        return userService.updateUser(user);
    }
}
