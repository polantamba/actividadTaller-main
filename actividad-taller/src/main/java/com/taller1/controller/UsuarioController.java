package com.taller1.controller;

import com.taller1.entity.Usuario;
import com.taller1.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired private UsuarioService usuarioService;

    @GetMapping("")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarUsuarios());
        return "pages/lista";
    }

    @GetMapping("/nuevo")
    public String nuevoUsuarioAdmin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "pages/registroAdmin";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        Usuario u = usuarioService.buscarPorId(id);
        u.setPassword("");
        model.addAttribute("usuario", u);
        return "pages/registroAdmin";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return "redirect:/usuarios";
    }

    @PostMapping("/guardar")
    public String guardarUsuarioAdmin(@Valid @ModelAttribute Usuario usuario, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "pages/registroAdmin";
        }
        try {
            usuarioService.guardarUsuario(usuario);
        } catch (Exception e) {
            return "pages/registroAdmin";
        }
        return "redirect:/usuarios";
    }
}