package com.crud.crudback.service;

import com.crud.crudback.entity.Chip;
import com.crud.crudback.entity.Mascota;
import com.crud.crudback.entity.Propietario;
import com.crud.crudback.repositories.MascotaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*Para más información se puede ver en PropietariosService*/
@Service
public class MascotaService {
    
    @Autowired
    private MascotaRepository mascotarepository;
    
    public Integer guardarMascota(String nombre, String especie, Propietario propietario, Chip chip){
        Mascota mascota = new Mascota();
        mascota.setNombre(nombre);
        mascota.setEspecie(especie);
        mascota.setPropietario(propietario);
        mascota.setChip(chip);
        mascota = mascotarepository.save(mascota);
        return mascota.getIdMascota();
    }
    
    public List<Mascota> listaMascotas() {
        return  mascotarepository.findAll();
    }
    
    public Optional<Mascota> getMascotas(int id) {
        return  mascotarepository.findById(id);
    }
    
    public Mascota obtenerPorId(Integer id) {
	return mascotarepository.findById(id).get();
    }
    
    public boolean existseId(int id){
        return mascotarepository.existsById(id);
    }
    
    public void actualizarMascota(Mascota mascota) {
	mascotarepository.save(mascota);
    }
    
    public void eliminarMascota(Integer id) {
	mascotarepository.deleteById(id);		
    }
        
}
