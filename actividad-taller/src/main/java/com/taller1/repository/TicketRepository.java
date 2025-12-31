package com.taller1.repository;

import com.taller1.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByUsuarioId(Long usuarioId);
    List<Ticket> findByUsuarioIsNull();
    List<Ticket> findByEstadoNombre(String nombre);

    long countByEstadoNombre(String nombre);
    long countByUsuarioId(Long usuarioId);
}