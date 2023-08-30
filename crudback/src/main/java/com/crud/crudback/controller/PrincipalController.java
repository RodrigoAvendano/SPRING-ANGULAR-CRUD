package com.crud.crudback.controller;

import com.crud.crudback.entity.UserEntity;
import com.crud.crudback.entity.Chip;
import com.crud.crudback.entity.ERole;
import com.crud.crudback.entity.RoleEntity;
import com.crud.crudback.dto.CreateUserDTO;
import com.crud.crudback.repositories.UserRepository;
import com.crud.crudback.security.jwt.JwtUtils;
import com.crud.crudback.repositories.ChipRepository;
import com.crud.crudback.repositories.RoleRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.crud.crudback.service.NewUserService;
import com.crud.crudback.service.UserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@CrossOrigin("*")
public class PrincipalController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NewUserService userService;

    @Autowired
    public UserDetailsServiceImpl userDetails;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;


     @PostMapping("/generate-token")
    public ResponseEntity<?> generarToken(@RequestBody String user, String pass) throws Exception {
        /* Autenticar */
         try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user,pass));
        }catch (DisabledException exception){
            throw  new Exception("USUARIO DESHABILITADO " + exception.getMessage());
        }catch (BadCredentialsException e){
            throw  new Exception("Credenciales invalidas " + e.getMessage());
        }
        /* Busca al usuario */
        UserDetails userDetails =  this.userDetails.loadUserByUsername(user);
        /*Generar token*/
        String token = this.jwtUtils.generateAccesToken(user);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/createUser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO createUserDTO){

        /*Se recibe un Set de tipo String con los nombres de los roles
        y se convierte a un SetRol Entity de Rol*/

        Set<RoleEntity> roles = createUserDTO.getRoles().stream()
                .map(role -> RoleEntity.builder()
                        .name(ERole.valueOf(role))
                        .build())
                .collect(Collectors.toSet());
                
        /* Construye el objeto por partes con el builder */
        UserEntity userEntity = UserEntity.builder()
                .username(createUserDTO.getUsername())
                .password(passwordEncoder.encode(createUserDTO.getPassword()))
                .email(createUserDTO.getEmail())
                .roles(roles)
                .build();

        userRepository.save(userEntity);

        return ResponseEntity.ok(userEntity);
    }

    /* 
    @GetMapping("/listaUser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserRepository>> listaUser(){
            List<UserRepository> usuarios = userService.findAll();
            return new ResponseEntity<List<UserRepository>>(usuarios, HttpStatus.OK);
    }
    */


    @DeleteMapping("/deleteUser/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(@RequestParam String id){
        userRepository.deleteById(Long.parseLong(id));
        return "Se ha borrado el user con id ".concat(id);
    }
}