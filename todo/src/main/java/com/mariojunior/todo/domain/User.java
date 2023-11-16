package com.mariojunior.todo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable= false, unique = true)
    private Long id;


    @Column(nullable = false, length = 100, unique = true)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //Nunca retornar a senha para o usuário.
    @Column(nullable = false, length = 60)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks = new ArrayList<Task>();

}
