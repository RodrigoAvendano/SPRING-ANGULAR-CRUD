package com.crud.crudback.security;

import com.crud.crudback.security.filters.JwtAuthenticationFilter;
import com.crud.crudback.security.filters.JwtAuthorizationFilter;
import com.crud.crudback.security.jwt.JwtUtils;
import com.crud.crudback.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    JwtAuthorizationFilter authorizationFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        /* Ruta a donde dirigir, por defecto es login */
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");
        System.out.println("Entrada a JWT Filter despupes de ruta ");
        return httpSecurity
                .cors()
                .and()
                .authorizeHttpRequests(auth -> { /* Administrar peticiones*/
                    //auth.requestMatchers("/login").permitAll();
                    auth.anyRequest().authenticated(); /* Si se desea acceder a otra ruta debe estar autenticado el usuario */
                })
                .csrf().disable()
                .sessionManagement(session -> { /* Administrar sesiones */
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS); /* Creación de sesión */
                })
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class) /* El filtro se ejecuta antes*/
                .build();
    }

    /* Encripta la contraseña */
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
   }

    /* Administra la autenticación */
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and().build();
    }
}