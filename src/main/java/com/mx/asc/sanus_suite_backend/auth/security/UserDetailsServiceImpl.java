package com.mx.asc.sanus_suite_backend.auth.security;

import com.mx.asc.sanus_suite_backend.auth.entities.UserEntity;
import com.mx.asc.sanus_suite_backend.auth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // Buscamos en la BD, si no existe disparamos el error de seguridad
    UserEntity user = userRepository.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el correo: " + username));

    // Lo envolvemos en nuestra implementación adaptada
    return new UserDetailsImpl(user);
  }
}
