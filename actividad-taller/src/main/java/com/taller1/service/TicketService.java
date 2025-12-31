package com.taller1.service;

import com.taller1.entity.Categoria;
import com.taller1.entity.Estado;
import com.taller1.entity.Ticket;
import com.taller1.entity.Usuario;
import com.taller1.repository.CategoriaRepository;
import com.taller1.repository.EstadoRepository;
import com.taller1.repository.TicketRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TicketService {

    @Autowired private TicketRepository ticketRepository;
    @Autowired private CategoriaRepository categoriaRepository;
    @Autowired private EstadoRepository estadoRepository;

    public List<Ticket> listarTodos() { return ticketRepository.findAll(); }
    public List<Ticket> listarPorUsuario(Long id) { return ticketRepository.findByUsuarioId(id); }

    // CAMBIO CRITICO AQUI: Ahora lista los "Abiertos" en lugar de los NULL
    public List<Ticket> listarSinAsignar() {
        return ticketRepository.findByEstadoNombre("Abierto");
    }

    public Ticket buscarPorId(Long id) { return ticketRepository.findById(id).orElse(null); }
    public void guardarTicket(Ticket ticket) { ticketRepository.save(ticket); }
    public void eliminarTicket(Long id) { ticketRepository.deleteById(id); }

    public void asignarTicket(Long ticketId, Usuario tecnico) {
        Ticket t = buscarPorId(ticketId);
        if(t != null) {
            t.setUsuario(tecnico);
            Estado enProceso = estadoRepository.findById(2L).orElse(null);
            if(enProceso != null) t.setEstado(enProceso);
            ticketRepository.save(t);
        }
    }

    public List<Categoria> listarCategorias() { return categoriaRepository.findAll(); }
    public List<Estado> listarEstados() { return estadoRepository.findAll(); }
    public Estado obtenerEstadoInicial() { return estadoRepository.findById(1L).orElse(null); }

    public long contarTodos() { return ticketRepository.count(); }
    public long contarPorEstado(String estado) { return ticketRepository.countByEstadoNombre(estado); }
    public long contarPorUsuario(Long id) { return ticketRepository.countByUsuarioId(id); }

    @PostConstruct
    public void inicializarDatos() {
        if (categoriaRepository.count() == 0) {
            crearCategoria("Hardware", "HRD-01", "Fallos fisicos", 24);
            crearCategoria("Software", "SFT-01", "Fallos logicos", 12);
            crearCategoria("Redes", "NET-01", "Conectividad", 4);
            crearCategoria("Accesos", "ACC-01", "Cuentas y Claves", 2);
        }
        if (estadoRepository.count() == 0) {
            crearEstado("Abierto", "Ticket creado", 1, 0);
            crearEstado("En Proceso", "Tecnico trabajando", 2, 50);
            crearEstado("Cerrado", "Finalizado", 3, 100);
        }
    }

    private void crearCategoria(String nombre, String codigo, String desc, Integer tiempo) {
        Categoria c = new Categoria();
        c.setNombre(nombre); c.setCodigo(codigo); c.setDescripcion(desc); c.setTiempoMaximoResolucion(tiempo);
        categoriaRepository.save(c);
    }
    private void crearEstado(String nombre, String desc, Integer orden, Integer progreso) {
        Estado e = new Estado();
        e.setNombre(nombre); e.setDescripcion(desc); e.setOrden(orden); e.setPorcentajeProgreso(progreso);
        estadoRepository.save(e);
    }
}