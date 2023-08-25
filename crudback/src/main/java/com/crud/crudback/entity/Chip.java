package com.crud.crudback.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Table(name="chip", schema="public")
@Entity
public class Chip {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idChip; 
    @Column(name="estado")
    private boolean estado;

    /*
    Relación Uno a Uno
    Mapeado por chip en Mascota
    */
    @OneToOne(cascade = CascadeType.ALL, mappedBy="chip")
    private Mascota mascota;

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
