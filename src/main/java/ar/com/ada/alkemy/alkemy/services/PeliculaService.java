package ar.com.ada.alkemy.alkemy.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.alkemy.alkemy.entities.Genero;
import ar.com.ada.alkemy.alkemy.entities.Pelicula;
import ar.com.ada.alkemy.alkemy.models.response.MoviesResponse;
import ar.com.ada.alkemy.alkemy.repos.PeliculaRepository;

@Service
public class PeliculaService {

    @Autowired
    PeliculaRepository repository;

    @Autowired
    GeneroService generoService;

    public Pelicula buscarPorPeliculaId(Integer id) {
        return repository.findByPeliculaId(id);
    }

    public Pelicula crearPelicula(Integer generoId, Integer calificacion, Date fechaCreacion,
    String imagen, String titulo) {
        Pelicula pelicula = new Pelicula();
        Genero genero = generoService.buscarPorGeneroId(generoId);
        pelicula.setCalificacion(calificacion);
        pelicula.setFechaCreacion(fechaCreacion);   
        pelicula.setImagen(imagen);
        pelicula.setTitulo(titulo);
        pelicula.setGenero(genero);
        genero.agregarPelicula(pelicula);
        return repository.save(pelicula);

    }

    public List<MoviesResponse> getPeliculas() {
        List<MoviesResponse> peliculas = new ArrayList<>();
        
        for (Pelicula pelicula : this.traerPeliculas()) {
            MoviesResponse listaPeliculas = new MoviesResponse();
            listaPeliculas.imagen = pelicula.getImagen();
            listaPeliculas.titulo = pelicula.getTitulo();
            listaPeliculas.fechaCreacion = pelicula.getFechaCreacion();

            peliculas.add(listaPeliculas);
        }
        return peliculas;

    }

    private List<Pelicula> traerPeliculas() {
        return repository.findAll();
    }

    public Pelicula modificarPelicula(Integer id, Integer generoId, Integer calificacion, Date fechaCreacion,
            String imagen, String titulo) {
        Pelicula pelicula = buscarPorPeliculaId(id);
        Genero genero = generoService.buscarPorGeneroId(generoId);
        pelicula.setCalificacion(calificacion);
        pelicula.setFechaCreacion(fechaCreacion);  
        pelicula.setImagen(imagen);
        pelicula.setTitulo(titulo);
        genero.agregarPelicula(pelicula);

        return repository.save(pelicula);

    }

}
