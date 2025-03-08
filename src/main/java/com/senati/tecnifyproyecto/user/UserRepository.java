package com.senati.tecnifyproyecto.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String correo);
    List<User> findByRole(Role role);
}
