package com.taller1.repository;
import com.taller1.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {}