package com.taller1.controller;

import com.taller1.entity.Ticket;
import com.taller1.entity.Usuario;
import com.taller1.repository.UsuarioRepository;
import com.taller1.roles.Rol;
import com.taller1.service.TicketService;
import com.taller1.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class TicketController {

    @Autowired private TicketService ticketService;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private UsuarioService usuarioService;

    @GetMapping("/panel")
    public String panel(Model model, Authentication authentication) {
        String username = authentication.getName();
        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);

        if (usuario == null) return "redirect:/login";

        if (usuario.getRol() == Rol.ROLE_ADMIN) {
            model.addAttribute("totalTickets", ticketService.contarTodos());
            model.addAttribute("ticketsAbiertos", ticketService.contarPorEstado("Abierto"));
            model.addAttribute("ticketsProceso", ticketService.contarPorEstado("En Proceso"));
            model.addAttribute("ticketsCerrados", ticketService.contarPorEstado("Cerrado"));
            model.addAttribute("usuariosTotal", usuarioService.listarUsuarios().size());
            model.addAttribute("tickets", ticketService.listarTodos());
            return "pages/panelAdmin";
        }
        else if (usuario.getRol() == Rol.ROLE_SOPORTE) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("misTickets", ticketService.listarPorUsuario(usuario.getId()));
            model.addAttribute("ticketsSinAsignar", ticketService.listarSinAsignar());
            model.addAttribute("ticketsCerrados", ticketService.contarPorEstado("Cerrado"));
            return "pages/panelSoporte";
        }
        else {
            model.addAttribute("usuario", usuario);
            model.addAttribute("misTickets", ticketService.listarPorUsuario(usuario.getId()));
            model.addAttribute("totalMisTickets", ticketService.contarPorUsuario(usuario.getId()));
            return "pages/panelUsuario";
        }
    }

    @GetMapping("/ticket/tomar/{id}")
    public String tomarTicket(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        Usuario soporte = usuarioRepository.findByUsername(username).orElse(null);
        if(soporte != null && (soporte.getRol() == Rol.ROLE_SOPORTE || soporte.getRol() == Rol.ROLE_ADMIN)) {
            ticketService.asignarTicket(id, soporte);
        }
        return "redirect:/panel";
    }

    @GetMapping("/ticket/nuevo")
    public String nuevoTicket(Model model) {
        model.addAttribute("ticket", new Ticket());
        cargarListas(model);
        return "pages/formTicket";
    }

    @PostMapping("/ticket/guardar")
    public String guardarTicket(@Valid @ModelAttribute Ticket ticket, BindingResult result, Authentication authentication, Model model) {
        if (result.hasErrors()) {
            cargarListas(model);
            return "pages/formTicket";
        }
        String username = authentication.getName();
        Usuario usuarioLogueado = usuarioRepository.findByUsername(username).orElse(null);

        if (ticket.getId() == null) {
            if (usuarioLogueado != null && usuarioLogueado.getRol() == Rol.ROLE_USUARIO) {
                ticket.setUsuario(usuarioLogueado);
            }
            if(ticket.getNombreSolicitante() == null) ticket.setNombreSolicitante(usuarioLogueado.getNombre());
            if(ticket.getContactoSolicitante() == null) ticket.setContactoSolicitante(usuarioLogueado.getEmail());
            if(ticket.getEstado() == null) ticket.setEstado(ticketService.obtenerEstadoInicial());
        } else {
            Ticket old = ticketService.buscarPorId(ticket.getId());
            if(old != null) {
                if(ticket.getUsuario() == null) ticket.setUsuario(old.getUsuario());
                ticket.setFechaCreacion(old.getFechaCreacion());
                if (ticket.getEstado() == null) ticket.setEstado(old.getEstado());
            }
        }
        ticketService.guardarTicket(ticket);
        return "redirect:/panel";
    }

    @GetMapping("/ticket/editar/{id}")
    public String editarTicket(@PathVariable Long id, Model model) {
        Ticket ticket = ticketService.buscarPorId(id);
        if (ticket != null) {
            model.addAttribute("ticket", ticket);
            cargarListas(model);
            return "pages/formTicket";
        }
        return "redirect:/panel";
    }

    @GetMapping("/ticket/eliminar/{id}")
    public String eliminarTicket(@PathVariable Long id) {
        ticketService.eliminarTicket(id);
        return "redirect:/panel";
    }

    private void cargarListas(Model model) {
        model.addAttribute("categorias", ticketService.listarCategorias());
        model.addAttribute("estados", ticketService.listarEstados());
    }
}