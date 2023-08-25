package com.crud.crudback.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

//Tabla
@Table(name="propietario", schema="public")
//Entity: La representación de información que necesitamos en nuestra aplicación.
@Entity
public class Propietario {
    
    //Id autoincremental
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idPropietario;
    
    //nombres de las columnas en la tabla - atributos
    
    @Column(name="nombre")
    private String nombre;
    
    @Column(name="apellido")
    private String apellido;
    
    @Column(name="direccion")
    private String direccion;
    
    @Column(name="telefono")
    private String telefono;
    
    
    //Relación Uno a muchos mapeado con el atributo propietario en tabla mascota
    //Las operaciones en cascada con JPA nos permiten propagar las operaciones que se aplican en una relación entre dos entidades
    //En este caso con ALL se aplican todos los tipos de cascada.
    @OneToMany (cascade = {CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "propietario")
    private List<Mascota> mascota = new ArrayList<>();

    
    //Getters y Setters de los atributos
    public Integer getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(Integer id) {
        this.idPropietario = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Mascota> getMascota() {
        return mascota;
    }

    public void setMascota(List<Mascota> mascota) {
        this.mascota = mascota;
    }
        
    
}
