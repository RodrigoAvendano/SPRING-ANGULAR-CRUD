package com.crud.crudback.controller;

import com.crud.crudback.dto.Mensaje;
import com.crud.crudback.dto.PropietarioDTO;
import com.crud.crudback.entity.Propietario;
import com.crud.crudback.service.PropietarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/*
Un Controller va a ser el punto de entrada de la aplicación, para ello 
se encarga de interceptar la llamada, adaptar los datos de entrada, 
procesarlos y devolverlos.

@RestController es un controller especial en RESTful especif
icacion y equivale 
a la suma de @Controller y @ResponseBody.
@RestController es añadida a partir de Spring 4.0 esto mejora la creación 
de RESTful web services y simplifica el uso de las anotaciones @Controller y 
@ResponseBody.
*/
@RestController
public class PropietarioController {
    
    //Inyecta la dependencia de Service
    @Autowired
    public PropietarioService propietarioService;
    
    /*Manejan las peticiones POST de HTTP Request
     @RequestBody sirve para deserializar un objeto completo a 
    partir del cuerpo de la petición
    */
    @PostMapping("/createProp")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Integer> guardarPropietario(@RequestBody PropietarioDTO data){
        Integer id = 0;
        try {
            id = this.propietarioService.guardarPropietario(data.getNombre(), data.getApellido(), data.getDireccion(), data.getTelefono(), data.getMascota());
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: "+e);
        }
        //ResponseEntity<> es una extensión de HttpEntity que añade un HttpStatusCode
        if(id == 0) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>(id,HttpStatus.OK);		
    }
    
    /*Manejan las peticiones GET de HTTP Request*/
    @GetMapping("/obtenerProp/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<PropietarioDTO> obtenerPropietario(@PathVariable("id") Integer id){
            Propietario persona = this.propietarioService.obtenerPorId(id);
            PropietarioDTO data = new PropietarioDTO(persona);
            return new ResponseEntity<>(data,HttpStatus.OK);
    }
    
    /*Maneja las peticiones de actualización o modificación*/
    @PutMapping("/actualizarProp/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> actualizarPropietario(@PathVariable("id") Integer id, @RequestBody PropietarioDTO data){
            Integer flag = 0;
            if(!propietarioService.existseId(id)) {
                    Mensaje message = new Mensaje("No existe el id a actualizar");
                    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
            }
            try {
                    Propietario propietario = propietarioService.getPropietarios(id).get();
                    propietario.setNombre(data.getNombre());
                    propietario.setApellido(data.getApellido());
                    propietario.setTelefono(data.getTelefono());
                    propietario.setDireccion(data.getDireccion());
                    propietarioService.actualizarPropietario(propietario);
                    flag = 1;
            }catch(Exception e) {
                    System.out.println("Error: "+e);
            }

            if(flag == 0) {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Mensaje message = new Mensaje("Datos modificados");
            return new ResponseEntity<>(message,HttpStatus.OK);
    }
    
    /*Maneja las peticiones de eliminación*/
    @DeleteMapping("/eliminarProp/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminarPropietario(@PathVariable("id") Integer id){
            if(!propietarioService.existseId(id)) {
                    Mensaje message = new Mensaje("No existe el id a eliminar");
                    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
            }
            propietarioService.eliminarPropietario(id);
            return new ResponseEntity<>(id,HttpStatus.OK);
    }
    
    @GetMapping("/listaProp")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<Propietario>> listaPropietarios(){
            List<Propietario> persona = propietarioService.listaPropietarios();
            return new ResponseEntity<List<Propietario>>(persona, HttpStatus.OK);
    }
}