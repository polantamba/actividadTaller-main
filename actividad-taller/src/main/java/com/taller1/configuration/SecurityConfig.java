package com.taller1.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/registro", "/guardarRegistro", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/usuarios").hasAnyRole("ADMIN", "SOPORTE")
                        .requestMatchers("/usuarios/**").hasRole("ADMIN")
                        .requestMatchers("/ticket/nuevo", "/ticket/guardar").hasAnyRole("ADMIN", "SOPORTE", "USUARIO")
                        .requestMatchers("/ticket/editar/**", "/ticket/eliminar/**").hasAnyRole("ADMIN", "SOPORTE")
                        .requestMatchers("/panel").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("username")
                        .defaultSuccessUrl("/login/postLogin", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );
        return http.build();
    }
}