package com.taller1.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String codigo;
    private String descripcion;
    private Integer tiempoMaximoResolucion;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Integer getTiempoMaximoResolucion() { return tiempoMaximoResolucion; }
    public void setTiempoMaximoResolucion(Integer tiempoMaximoResolucion) { this.tiempoMaximoResolucion = tiempoMaximoResolucion; }
}