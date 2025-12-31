package com.taller1.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "estados")
public class Estado {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private Integer orden;
    private Integer porcentajeProgreso;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }
    public Integer getPorcentajeProgreso() { return porcentajeProgreso; }
    public void setPorcentajeProgreso(Integer porcentajeProgreso) { this.porcentajeProgreso = porcentajeProgreso; }
}