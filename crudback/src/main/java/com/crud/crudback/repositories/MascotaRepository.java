package com.crud.crudback.repositories;

import com.crud.crudback.entity.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MascotaRepository extends JpaRepository<Mascota, Integer>{
    
}