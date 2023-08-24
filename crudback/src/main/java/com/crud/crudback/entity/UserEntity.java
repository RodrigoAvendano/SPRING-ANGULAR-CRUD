package com.crud.crudback.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data /*Genera getters y setters con lombok*/
@AllArgsConstructor /*Constructor con todos los argumentos*/
@NoArgsConstructor /*Constructor sin parámetros*/
@Builder /*Patrón de diseño builder para construir objetos de esta clase*/
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotBlank
    @Size(max = 80)
    private String email;

    @NotBlank
    @Size(max = 30)
    private String username;

    @NotBlank
    private String password;

    /* 
        EAGER: Cuando se consulte el usuario traerá todos los roles asociados
        targetEntity = Relacionado con la clase Rol
        PERSIST: Inserta el usuario pero si este se elimina no elimina los roles
   
        Tabla intermedia llamada user_roles
        @JoinColumn(name = "user_id") será la llave foránea de usuario
        @JoinColumn("role_id") será la llave fóraena de rol
    */

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = RoleEntity.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles;
}