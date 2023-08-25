package com.crud.crudback.repositories;

import com.crud.crudback.entity.Chip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChipRepository extends JpaRepository<Chip, Integer>{
    
}