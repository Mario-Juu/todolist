package com.mariojunior.todo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mariojunior.todo.domain.enums.ProfileEnums;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable= false, unique = true)
    private Long id;


    @Column(nullable = false, length = 100, unique = true)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //Nunca retornar a senha para o usuário.
    @Column(nullable = false, length = 60)
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Task> tasks = new ArrayList<Task>();

    @ElementCollection(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @CollectionTable(name = "user_profile")
    @Column(name = "profile_id", nullable = false)
    private Set<Integer> profiles = new HashSet<>();

    public Set<ProfileEnums> getProfiles(){
        return profiles.stream().map(x -> ProfileEnums.toEnum(x)).collect(Collectors.toSet());
    }

    public void addProfile(ProfileEnums profileEnums){
        profiles.add(profileEnums.getCode());
    }
}
