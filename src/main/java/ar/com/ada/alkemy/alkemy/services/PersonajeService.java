package ar.com.ada.alkemy.alkemy.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.alkemy.alkemy.entities.Pelicula;
import ar.com.ada.alkemy.alkemy.entities.Personaje;
import ar.com.ada.alkemy.alkemy.models.response.CharactersResponse;
import ar.com.ada.alkemy.alkemy.repos.PersonajeRepository;

@Service
public class PersonajeService {

    @Autowired
    PersonajeRepository repository;

    @Autowired
    PeliculaService peliculaService;

    public List<Personaje> traerPersonajes(){
        return repository.findAll();
    }

    public List<CharactersResponse> getPersonajes(String imagen, String nombre) {
        List<CharactersResponse> listaPersonajes = new ArrayList<>();

        for (Personaje personaje : this.traerPersonajes()) {
            CharactersResponse listaPersonajesImagenNombre = new CharactersResponse();
            listaPersonajesImagenNombre.imagen = personaje.getImagen();
            listaPersonajesImagenNombre.nombre = personaje.getNombre();

            listaPersonajes.add(listaPersonajesImagenNombre);
        }
        return listaPersonajes;
    }

    public Personaje crearPersonaje(Integer edad, String historia, String imagen, String nombre, double peso,
            Integer peliculaId) {
        Personaje personaje = new Personaje();
        Pelicula pelicula = peliculaService.buscarPorPeliculaId(peliculaId);
        personaje.setEdad(edad);
        personaje.setHistoria(historia);
        personaje.setImagen(imagen);
        personaje.setNombre(nombre);
        personaje.setPeso(peso);
        personaje.setPelicula(pelicula);
        pelicula.agregarPersonaje(personaje);

        return repository.save(personaje);
    }

    public Personaje buscarPersonajePorId(Integer id) {
        return repository.findByPersonajeId(id);
    }

    public void guardar(Personaje personaje) {
        repository.save(personaje);
    }

    public Personaje modificarPersonaje(Integer id, Integer peliculaId, Integer edadNueva, String historiaNueva,
            String imagenNueva, double pesoNuevo) {
        Personaje personaje = buscarPersonajePorId(id);
        Pelicula pelicula = peliculaService.buscarPorPeliculaId(peliculaId);
        personaje.setEdad(edadNueva);
        personaje.setHistoria(historiaNueva);
        personaje.setImagen(imagenNueva);
        personaje.setPelicula(pelicula);
        personaje.setPeso(pesoNuevo);
        pelicula.agregarPersonaje(personaje);
        return repository.save(personaje);
    }

    public void borrarPersonajePorId(Integer id) {
        repository.deleteById(id);
    }

    public Personaje getPersonaje(Integer id) {
        return repository.findByPersonajeId(id);
    }

    public Personaje getPersonaje(String nombre) {
        return repository.findByNombre(nombre);
    }

    public List<Personaje> traerPersonajesPorPelicula(Integer idMovie){

        Pelicula pelicula = peliculaService.buscarPorPeliculaId(idMovie);

        return pelicula.getPersonajes();
    }

}
