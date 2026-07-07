package com.mx.asc.sanus_suite_backend.auth.repositories;

import com.mx.asc.sanus_suite_backend.auth.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  // Busca al usuario activo por su correo electrónico
  Optional<UserEntity> findByUsername(String username);
  boolean existsByUsername(String username);
}