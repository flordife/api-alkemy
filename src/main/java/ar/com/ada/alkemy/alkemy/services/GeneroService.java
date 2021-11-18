package ar.com.ada.alkemy.alkemy.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.alkemy.alkemy.entities.Genero;
import ar.com.ada.alkemy.alkemy.repos.GeneroRepository;

@Service
public class GeneroService {

    @Autowired
    GeneroRepository repository;

    public Genero buscarPorGeneroId(Integer id) {
        return repository.findByGeneroId(id);
    }

    public void crearGenero(Genero genero) {
        repository.save(genero);
    }

    public List<Genero> traerTodos(){
        return repository.findAll();
    }

    public Genero buscarPorNombre(String nombre) {
        return repository.findByNombre(nombre);
    }
    
}
