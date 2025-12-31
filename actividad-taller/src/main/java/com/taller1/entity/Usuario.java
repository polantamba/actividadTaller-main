package com.taller1.entity;

import com.taller1.roles.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^\\d{10}$", message = "El teléfono debe contener exactamente 10 números")
    private String telefono;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un formato de correo válido")
    private String email;

    @Column(unique = true)
    @NotBlank(message = "El usuario es obligatorio")
    @Size(min = 4, message = "El usuario debe tener al menos 4 caracteres")
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
}