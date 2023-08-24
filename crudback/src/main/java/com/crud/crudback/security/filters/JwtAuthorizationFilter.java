package com.crud.crudback.security.filters;

import com.crud.crudback.security.jwt.JwtUtils;
import com.crud.crudback.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/* Se autoriza cada endpoint */
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /* Los parámetros siempre deben estar presentes */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        /* Extraer el token */                                
        String tokenHeader = request.getHeader("Authorization");

        /* Si el token es null se rechaza la conexión, los JWT siempre inician con Bearer */
        if(tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            /* Obtener token sin la palabra Bearer */
            String token = tokenHeader.substring(7);

            if(jwtUtils.isTokenValid(token)){
                /* Obtener el nombre de usuario */
                String username = jwtUtils.getUsernameFromToken(token);
                /* Recuperar usuario, contraseña, roles del usuario */
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                /* Autenticar (usuario, contraseña(obtenida por UserDetails), roles) */
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                /* Setear autenticación nueva del usaurio */
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        /* Continuar con el filtro de validación */
        filterChain.doFilter(request, response);
    }
}