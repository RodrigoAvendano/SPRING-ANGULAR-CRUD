package com.crud.crudback.dto;

import com.crud.crudback.entity.Mascota;
import com.crud.crudback.entity.Propietario;
import java.util.List;
/*Los DTO son un tipo de objetos que sirven únicamente para transportar 
datos. EL DTO contiene las propiedades del objeto. Datos que pueden tener su 
origen en una o más entidades de información. Estos datos son 
incorporados a una instancia de un JavaBean*/
public class PropietarioDTO {
    
    public Integer idPropietario;
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private List<Mascota> mascota;
    
    public PropietarioDTO(){
    }
    
    public PropietarioDTO(Propietario propietario){
        this.idPropietario = propietario.getIdPropietario();
        this.nombre = propietario.getNombre();
        this.apellido = propietario.getApellido();
        this.direccion = propietario.getDireccion();
        this.telefono = propietario.getTelefono();
    }

    public Integer getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(Integer idPropietario) {
        this.idPropietario = idPropietario;
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
