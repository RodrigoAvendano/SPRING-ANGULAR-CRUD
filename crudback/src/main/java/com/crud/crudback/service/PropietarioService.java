package com.crud.crudback.service;

import com.crud.crudback.entity.Mascota;
import com.crud.crudback.entity.Propietario;
import com.crud.crudback.repositories.PropietarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 Se usa para construir una clase de Servicio, el servicio habitualmente se conecta a varios repositorios y agrupa su funcionalidad.
*/
@Service
public class PropietarioService {
    
    /*
    La anotación @Autowired nos proporciona control a la hora de querer inyectar
    nuestras dependencias o instancias que se almacenan en el contexto de Spring
    Se puede usar la anotación @Autowired en las propiedades. 
    Las propiedades se inyectan justo después de la construcción 
    de un bean (instancia de la clase), antes de que se invoque cualquier método
    de configuración.
    La propiedad no tiene por qué ser public.
    Se utiliza el Repository para traer los métodos de JPA que se encuentran en esta.
    */
    @Autowired
    private PropietarioRepository propietarioRepository;
    
    
    /*
    Guardar propietario recibe diferentes parámetros, usando los setters de la 
    entidad Propietario para guardar la información.
    */
    public Integer guardarPropietario(String nombre, String apellido, String direccion, String telefono, List<Mascota> mascota){
        Propietario propietario = new Propietario();
        propietario.setNombre(nombre);
        propietario.setApellido(apellido);
        propietario.setDireccion(direccion);
        propietario.setTelefono(telefono);
        propietario.setMascota(mascota);
        /*Guarda la entidad en la Base de datos*/
        propietario = propietarioRepository.save(propietario);
        return propietario.getIdPropietario();
    }
    
    public List<Propietario> listaPropietarios() {
        /*Muestra todos los registros*/
        return  propietarioRepository.findAll();
    }
    
    public Optional<Propietario> getPropietarios(int id) {
        /*Encuentra el registro por el id*/
        return propietarioRepository.findById(id);
    }
    
    public Propietario obtenerPorId(Integer id) {
        /*Obtiene un registro determinado por id */
	return propietarioRepository.findById(id).get();
    }
    
    public boolean existseId(int id){
        /*Verifica si el id existe en la entidad*/
        return propietarioRepository.existsById(id);
    }
    
    public void actualizarPropietario(Propietario propietario) {
        /*Guarda la propiedad en la base de datos*/
	propietarioRepository.save(propietario);
    }
    
    public void eliminarPropietario(Integer id) {
        /*Elimina el registro con el id brindado*/
	propietarioRepository.deleteById(id);		
    }
	
    
}