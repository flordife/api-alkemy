package ar.com.ada.alkemy.alkemy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.alkemy.alkemy.entities.Genero;
import ar.com.ada.alkemy.alkemy.entities.Pelicula;
import ar.com.ada.alkemy.alkemy.models.request.InfoPeliculaActualizada;
import ar.com.ada.alkemy.alkemy.models.response.GenericResponse;
import ar.com.ada.alkemy.alkemy.models.response.MoviesResponse;
import ar.com.ada.alkemy.alkemy.services.*;

@RestController
public class PeliculaController {

    @Autowired
    PeliculaService service;

    @Autowired
    GeneroService generoService;

    @PostMapping("/movies")
    public ResponseEntity<GenericResponse> postPeliculas(@RequestBody Pelicula pelicula, Integer id) {

        service.crearPelicula(pelicula);
        Genero genero = generoService.buscarPorGeneroId(id);
        genero.agregarPelicula(pelicula);

        GenericResponse respuesta = new GenericResponse();
        respuesta.isOk = true;
        respuesta.id = pelicula.getPeliculaId();
        respuesta.mensaje = "La película ha sido creada con exito.";

        return ResponseEntity.ok(respuesta);

    }

    @GetMapping("/movies")
    public ResponseEntity<List<MoviesResponse>> getPeliculas() {
        return ResponseEntity.ok(service.getPeliculas());
    }

    @PutMapping("/movies/{id}")
    public ResponseEntity<GenericResponse> putPeliculas(@PathVariable Integer id,
            @RequestBody InfoPeliculaActualizada peliculaActualizada) {

        Pelicula pelicula = service.modificarPelicula(id, peliculaActualizada.calificacion,
                peliculaActualizada.generoId, peliculaActualizada.fechaCreacion, peliculaActualizada.imagen,
                peliculaActualizada.titulo);

        GenericResponse respuesta = new GenericResponse();
        respuesta.isOk = true;
        respuesta.id = pelicula.getPeliculaId();
        respuesta.mensaje = "La película ha sido actualizada.";

        return ResponseEntity.ok(respuesta);
    }

}
