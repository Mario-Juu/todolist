package com.mariojunior.todo.repository;

import com.mariojunior.todo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
