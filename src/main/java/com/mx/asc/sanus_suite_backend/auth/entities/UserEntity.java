package com.mx.asc.sanus_suite_backend.auth.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false, length = 100)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(name = "full_name", nullable = false, length = 150)
  private String fullName;

  @Column(name = "tenant_id", nullable = false, length = 50)
  private String tenantId;

  private boolean enabled = true;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
  @Enumerated(EnumType.STRING)
  @Column(name = "role_name")
  private Set<RoleEnum> roles;
}
