package com.crud.crudback.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

//Nombre de la tabla
@Table(name="mascota", schema="public")
//Entity: La representación de información que necesitamos en nuestra aplicación.
@Entity
public class Mascota {
    //Id autoincremental
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idMascota;  
    private String nombre;
    private String especie;
    
    /*@ManyToOne: Relación Muchos a uno
    CascadeType.MERGE = Propaga la actualización en la base de datos de una entidad a las entidades relacionadas.
    Cuando modificamos una entidad y la entidad relacionada, ambas se modifican al mismo tiempo 
    y no se genera un nuevo registro de la entidad relacionada en la base de datos.
    */
    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.REFRESH})
    @JoinColumn(name = "idPropietario")
    @JsonIgnore
    private Propietario propietario;
    
    
    /*
    Relación Uno a Uno
    MERGE:Las entidades relacionadas se unirán al contexto de persistencia cuando la entidad propietaria se una.
    REMOVE: las entidades relacionadas se eliminan de la base de datos cuando la entidad propietaria se elimine
    REFRESH: las entidades relacionadas actualizan sus datos desde la base de datos cuando la entidad propietaria se actualiza.
    */
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})
    @JoinColumn(name = "idChip")
    @JsonIgnore
    private Chip chip; 

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