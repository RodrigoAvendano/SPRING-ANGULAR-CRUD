package com.crud.crudback.service;

import com.crud.crudback.entity.Chip;
import com.crud.crudback.entity.Mascota;
import com.crud.crudback.repositories.ChipRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*Para más información se puede ver en PropietariosService*/
@Service
public class ChipService {
    
    @Autowired
    private ChipRepository chipRepository;
    
    public Integer guardarChip(boolean estado, Mascota mascota){
        Chip chip = new Chip();
        chip.setEstado(estado);
        chip.setMascota(mascota);
        chip = chipRepository.save(chip);
        return chip.getIdChip();
    }
    
    public List<Chip> listaChips() {
        return  chipRepository.findAll();
    }
    
    public Optional<Chip> getChips(int id) {
        return  chipRepository.findById(id);
    }
    
    public Chip obtenerPorId(Integer id) {
	return chipRepository.findById(id).get();
    }
    
    public boolean existseId(int id){
        return chipRepository.existsById(id);
    }
    
    public void actualizarChip(Chip chip) {
	chipRepository.save(chip);
    }
    
    public void eliminarChip(Integer id) {
	chipRepository.deleteById(id);		
    }
    
}
