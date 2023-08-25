package com.crud.crudback.dto;

import com.crud.crudback.entity.Mascota;
import com.crud.crudback.entity.Propietario;
import com.crud.crudback.entity.Chip;

//La información de DTO se da en PropietarioDTO
public class MascotaDTO {
    
    public Integer idMascota;  
    private String nombre;
    private String especie;    
    private Propietario propietario;
    private Chip chip; 
    
    public MascotaDTO(){
    }
    
    public MascotaDTO(Mascota mascota){
        this.idMascota = mascota.getIdMascota();
        this.nombre = mascota.getNombre();
        this.especie = mascota.getEspecie();
        this.propietario = mascota.getPropietario();
        this.chip = mascota.getChip();
    }

    public Integer getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(Integer idMascota) {
        this.idMascota = idMascota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public Chip getChip() {
        return chip;
    }

    public void setChip(Chip chip) {
        this.chip = chip;
    }
    
    
}