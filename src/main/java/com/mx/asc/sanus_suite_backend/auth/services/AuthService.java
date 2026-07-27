package com.mx.asc.sanus_suite_backend.auth.services;

import com.mx.asc.log.bean.LogBean;
import com.mx.asc.log.service.LoggerAscService;
import com.mx.asc.sanus_suite_backend.auth.dtos.AuthResponseDto;
import com.mx.asc.sanus_suite_backend.auth.dtos.LoginRequestDto;
import com.mx.asc.sanus_suite_backend.auth.dtos.UserRegisterDto;
import com.mx.asc.sanus_suite_backend.auth.entities.RoleEnum;
import com.mx.asc.sanus_suite_backend.auth.entities.UserEntity;
import com.mx.asc.sanus_suite_backend.auth.repositories.UserRepository;
import com.mx.asc.sanus_suite_backend.auth.security.JwtUtils;
import com.mx.asc.sanus_suite_backend.auth.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final JwtUtils jwtUtils;
  private final PasswordEncoder passwordEncoder;
  private final LoggerAscService log;

  public AuthResponseDto authenticate(LoginRequestDto request) {

    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Iniciando metodo authenticate] "))
      .build());

    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[validando credenciales con Spring security] "))
      .build());
    // 1. Spring Security valida las credenciales (compara la clave plana contra el hash BCrypt de la BD)
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
    );

    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Guardando la autenticacion con el contexto de seguridad actual] "))
      .build());
    // 2. Guardamos la autenticación en el contexto de seguridad actual
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // 3. Recuperamos el UserDetailsImpl que creamos previamente para tener acceso rápido al clinicId
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[UserDetails recuperado exitosamente] "))
      .build());

    // 4. Buscamos la entidad completa en la base de datos para mapearla al generador de tokens
    UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername())
      .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado post-autenticación."));
    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[usuario encontrado en la base de datos] "))
      .build());

    // 5. Generamos el token JWT firmado de 8 horas
    String jwtToken = jwtUtils.generateJwtToken(userEntity);
    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Token generado exitosamente] "))
      .build());

    // 6. Mapeamos los roles a una colección de Strings para el frontend
    Set<String> roles = userDetails.getAuthorities().stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.toSet());

    // 7. Retornamos el objeto estructurado con los datos vitales de sesión
    return AuthResponseDto.builder()
      .token(jwtToken)
      .username(userDetails.getUsername())
      .fullName(userEntity.getFullName())
      .tenantId(userDetails.getTenantId())
      .roles(roles)
      .build();
  }

  // Agrega este método dentro de tu AuthService existente
  public void register(UserRegisterDto dto) {
    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Iniciando metodo register] "))
      .build());
    // 1. Validar que el usuario no exista previamente
    if (userRepository.existsByUsername(dto.getUsername())) {
      throw new IllegalArgumentException("El correo ya se encuentra registrado");
    }

    // 2. Construir la entidad encriptando el password en caliente
    UserEntity newUser = UserEntity.builder()
      .username(dto.getUsername())
      .fullName(dto.getFullName())
      .password(passwordEncoder.encode(dto.getPassword())) // <--- Clave de la encriptación
      .tenantId(dto.getTenantId())
      .enabled(true)
      .roles(Set.of(RoleEnum.ROLE_ADMIN)) // Rol por defecto para tus pruebas locales
      .build();

    // 3. Persistir en base de datos
    userRepository.save(newUser);
    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Usuario registrado exitosamente...] "))
      .build());
  }
}