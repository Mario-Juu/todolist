package com.mariojunior.todo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference //Diz que esse é o lado "muitos" da relação
    private User user;

    @Column(nullable = false)
    private String description;
}
