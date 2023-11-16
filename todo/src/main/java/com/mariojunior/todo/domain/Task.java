package com.mariojunior.todo.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne //Um usuário pode ter várias tarefas
    @JoinColumn(updatable = false) //Obrigatório ter um usuário
    private User user;

    @Column(nullable = false)
    private String description;
}
