package com.crud.crudback.dto;


import com.crud.crudback.entity.Chip;
import com.crud.crudback.entity.Mascota;

//La información de DTO se da en PropietarioDTO
public class ChipDTO {
    
    public Integer idChip; 
    private boolean estado;
    private Mascota mascota;
    
    public ChipDTO(){
    }
    
    public ChipDTO(Chip chip){
        this.idChip = chip.getIdChip();
        this.estado = chip.getEstado();
        this.mascota = chip.getMascota();
    }

    public Integer getIdChip() {
        return idChip;
    }

    public void setIdChip(Integer idChip) {
        this.idChip = idChip;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }
    
    
    
}