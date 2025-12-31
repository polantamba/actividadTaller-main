package com.taller1.service;

import com.taller1.entity.Usuario;
import com.taller1.repository.UsuarioRepository;
import com.taller1.roles.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario guardarUsuario(Usuario usuario) {
        if (usuario.getId() == null || (usuario.getPassword() != null && !usuario.getPassword().isEmpty())) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        } else {
            Usuario viejo = usuarioRepository.findById(usuario.getId()).orElse(null);
            if(viejo != null) {
                usuario.setPassword(viejo.getPassword());
            }
        }

        if (usuario.getRol() == null) {
            usuario.setRol(Rol.ROLE_USUARIO);
        }

        return usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(usuario.getRol().name());

        return new User(usuario.getUsername(), usuario.getPassword(), Collections.singletonList(authority));
    }
}