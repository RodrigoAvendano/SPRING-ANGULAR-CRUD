package com.crud.crudback.repositories;

import com.crud.crudback.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    /*
    Crea una consulta para obtener los nombres de usuario
    Crea un método basado en el query para buscar el nombre de usuario
    */

    @Query("select u from UserEntity u where u.username = ?1")
    Optional<UserEntity> getName(String username);
}