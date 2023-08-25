package com.crud.crudback.controller;

import com.crud.crudback.dto.ChipDTO;
import com.crud.crudback.dto.Mensaje;
import com.crud.crudback.entity.Chip;
import com.crud.crudback.service.ChipService;
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
public class ChipController {
    
    @Autowired
    public ChipService chipService;
    
    @PostMapping("/createChip")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Integer> guardarChip(@RequestBody ChipDTO data){
        Integer id = 0;
        try {
            id = this.chipService.guardarChip(data.getEstado(), data.getMascota());
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error");
        }
        if(id == 0) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>(id,HttpStatus.OK);		
    }
    
    @GetMapping("/obtenerChip/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<ChipDTO> obtenerChip(@PathVariable("id") Integer id){
            Chip chip = this.chipService.obtenerPorId(id);
            ChipDTO data = new ChipDTO(chip);
            return new ResponseEntity<>(data,HttpStatus.OK);
    }
    
    @PutMapping("/actualizarChip/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> actualizarChip(@PathVariable("id") Integer id, @RequestBody ChipDTO data){
            Integer flag = 0;
            if(!chipService.existseId(id)) {
                    Mensaje message = new Mensaje("No existe el id a actualizar");
                    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
            }
            try {
                    Chip chip = chipService.getChips(id).get();
                    chip.setEstado(data.getEstado());
                    chip.setMascota(data.getMascota());
                    chipService.actualizarChip(chip);
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
    
    @DeleteMapping("/eliminarChip/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminarChip(@PathVariable("id") Integer id){
            if(!chipService.existseId(id)) {
                    Mensaje message = new Mensaje("No existe el id a eliminar");
                    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
            }
            chipService.eliminarChip(id);
            return new ResponseEntity<>(id,HttpStatus.OK);
    }
    
    @GetMapping("/listaChips")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<Chip>> listaChip(){
            List<Chip> chip = chipService.listaChips();
            return new ResponseEntity<List<Chip>>(chip, HttpStatus.OK);
    }
    
}
