package com.crud.crudback.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.crudback.entity.UserEntity;
import com.crud.crudback.repositories.UserRepository;

@Service
public class NewUserService {

    @Autowired
    UserRepository usuarioRepository;

    public List<UserEntity> listaUsers() {
        return  (List<UserEntity>) usuarioRepository.findAll();
    }
    
}
