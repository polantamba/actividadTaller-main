package com.taller1.controller;

import com.taller1.entity.Usuario;
import com.taller1.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired private UsuarioService usuarioService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "pages/login";
    }

    @GetMapping("/registro")
    public String registro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "pages/registro";
    }

    @PostMapping("/guardarRegistro")
    public String guardarRegistro(@ModelAttribute Usuario usuario) {
        usuarioService.guardarUsuario(usuario);
        return "redirect:/login?registrado=true";
    }

    @GetMapping("/login/postLogin")
    public String postLogin() {
        return "redirect:/panel";
    }
}