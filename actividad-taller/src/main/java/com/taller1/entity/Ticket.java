package com.taller1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El asunto es obligatorio")
    @Size(min = 10, message = "El asunto debe tener al menos 10 caracteres")
    private String titulo;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 10, message = "La descripción debe tener al menos 10 caracteres")
    private String descripcion;

    private String prioridad;

    @NotBlank(message = "La ubicación es obligatoria")
    private String ubicacion;

    @NotBlank(message = "El nombre del solicitante es obligatorio")
    private String nombreSolicitante;

    @NotBlank(message = "El contacto es obligatorio")
    private String contactoSolicitante;

    @NotNull(message = "Debe seleccionar una categoría")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estado_id")
    private Estado estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private LocalDateTime fechaCreacion;

    @PrePersist
    public void prePersist() {
        fechaCreacion = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public String getNombreSolicitante() { return nombreSolicitante; }
    public void setNombreSolicitante(String nombreSolicitante) { this.nombreSolicitante = nombreSolicitante; }
    public String getContactoSolicitante() { return contactoSolicitante; }
    public void setContactoSolicitante(String contactoSolicitante) { this.contactoSolicitante = contactoSolicitante; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}