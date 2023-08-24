package com.crud.crudback.security.filters;

import com.crud.crudback.entity.UserEntity;
import com.crud.crudback.security.jwt.JwtUtils;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* Ayuda a autenticar en la aplicación */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JwtUtils jwtUtils;

    /* Inyección de JwtUtils*/
    public JwtAuthenticationFilter(JwtUtils jwtUtils){
        this.jwtUtils = jwtUtils;
    }

    /* Intentar autenticarse, request viene en formato JSON */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        UserEntity userEntity = null;
        String username = "";
        String password = "";
        try{
            userEntity = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class); /* Se mapea el Json con Jackson a Usuario */
            username = userEntity.getUsername();
            password = userEntity.getPassword();
        } catch (StreamReadException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /* Autenticar en la aplicación*/
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password); /* Nombre de usuario y contraseña para autenticarse */

        return getAuthenticationManager().authenticate(authenticationToken); /* Devuelve la autenticación cuando es correcta */
    }

    /* Autenticación exitosa */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        User user = (User) authResult.getPrincipal(); /* User de Spring Security - Objeto co todos los detalles del usaurio */
        String token = jwtUtils.generateAccesToken(user.getUsername());  /* Genera token de acceso para dar autorización a los endpoints */

        response.addHeader("Authorization", token); /* Añade el token al header */

        /* Envío de datos en la respuesta */
        Map<String, Object> httpResponse = new HashMap<>();
        httpResponse.put("token", token);
        httpResponse.put("Message", "Autenticacion Correcta");
        httpResponse.put("Username", user.getUsername());

        /* Escribir mapa como JSON en la respuesta */
        response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));
        /* Status de respuesta (200) */
        response.setStatus(HttpStatus.OK.value());
        /* Contenido de la respuesta */
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        /* Asegura que todo se escriba de forma correcta */
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }
}