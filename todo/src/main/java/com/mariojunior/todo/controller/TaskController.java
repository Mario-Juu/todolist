package com.mariojunior.todo.controller;

import com.mariojunior.todo.domain.Task;
import com.mariojunior.todo.service.exception.DataBindingViolationException;
import com.mariojunior.todo.service.exception.ResourceNotFoundException;
import com.mariojunior.todo.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/tasks", produces = {"application/json"})
@Tag(name = "todoList-api")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserController userController;


    @Operation(summary = "Realiza a busca de todas as tasks de um usuário", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca"),
    })
    @GetMapping("/")
    public List<Task> findAll(){
        return taskService.findAll();
    }


    @Operation(summary = "Realiza a busca de uma task por ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca"),
    })
    @GetMapping("/{id}")
    public Optional<Task> findById(@PathVariable Long id){
        try{
            return taskService.findById(id);
        } catch(ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task não encontrada", e);
        }
    }

    @Operation(summary = "Realiza a inserção de uma task", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Adicionado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a requisição"),
    })
    @PostMapping("/")
    public Optional<Task> addTask(@RequestBody Task task){
        return Optional.of(taskService.addTask(task));
    }


    @Operation(summary = "Realiza o delete de uma task", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deletado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o delete"),
    })
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

    @Operation(summary = "Realiza o update de uma task", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a atualização"),
    })
    @PutMapping("/")
    public Task editUser(@RequestBody Task task){
        return taskService.updateTask(task);
    }
}
