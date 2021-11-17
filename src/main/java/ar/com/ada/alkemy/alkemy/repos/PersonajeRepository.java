package ar.com.ada.alkemy.alkemy.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.alkemy.alkemy.entities.Personaje;

@Repository
public interface PersonajeRepository extends JpaRepository<Personaje, Integer> {

    Personaje findByPersonajeId(Integer id);

    Personaje findByNombre(String nombre);
    
}
