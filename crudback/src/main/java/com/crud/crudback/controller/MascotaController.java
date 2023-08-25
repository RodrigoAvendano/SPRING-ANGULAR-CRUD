package com.crud.crudback.controller;

import com.crud.crudback.dto.MascotaDTO;
import com.crud.crudback.dto.Mensaje;
import com.crud.crudback.entity.Mascota;
import com.crud.crudback.service.MascotaService;
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

/*La información sobre Controllers se puede ver en PropietariosController*/

@RestController
public class MascotaController {
    
    @Autowired
    public MascotaService mascotaService;
    
    @PostMapping("/createMasc")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Integer> guardarMascota(@RequestBody MascotaDTO data){
        Integer id = 0;
        try {
            id = this.mascotaService.guardarMascota(data.getNombre(),data.getEspecie(), data.getPropietario(), data.getChip());
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: "+e);
        }
        if(id == 0) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>(id,HttpStatus.OK);
    }
    
    @GetMapping("/obtenerMasc/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<MascotaDTO> obtenerMascota(@PathVariable("id") Integer id){
            Mascota mascota = this.mascotaService.obtenerPorId(id);
            MascotaDTO data = new MascotaDTO(mascota);
            return new ResponseEntity<>(data,HttpStatus.OK);
    }
    
    @PutMapping("/actualizarMasc/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> actualizarMascota(@PathVariable("id") Integer id, @RequestBody MascotaDTO data){
            Integer flag = 0;
            if(!mascotaService.existseId(id)) {
                    Mensaje message = new Mensaje("No existe el id a actualizar");
                    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
            }
            try {
                    Mascota mascota = mascotaService.getMascotas(id).get();
                    mascota.setNombre(data.getNombre());
                    mascota.setEspecie(data.getEspecie());
                    mascota.setPropietario(data.getPropietario());
                    mascota.setChip(data.getChip());
                    mascotaService.actualizarMascota(mascota);
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
    
    @DeleteMapping("/eliminarMasc/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminarMascota(@PathVariable("id") Integer id){
            if(!mascotaService.existseId(id)) {
                    Mensaje message = new Mensaje("No existe el id a eliminar");
                    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
            }
            mascotaService.eliminarMascota(id);
            return new ResponseEntity<>(id,HttpStatus.OK);
    }
    
    @GetMapping("/listaMasc")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<Mascota>> listaMascota(){
            List<Mascota> mascota = mascotaService.listaMascotas();
            return new ResponseEntity<List<Mascota>>(mascota, HttpStatus.OK);
    }
    
}
