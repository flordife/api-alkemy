package ar.com.ada.alkemy.alkemy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ar.com.ada.alkemy.alkemy.entities.Pelicula;
import ar.com.ada.alkemy.alkemy.entities.Usuario;
import ar.com.ada.alkemy.alkemy.models.request.InfoPeliculaActualizada;
import ar.com.ada.alkemy.alkemy.models.request.InfoPeliculaNueva;
import ar.com.ada.alkemy.alkemy.models.response.GenericResponse;
import ar.com.ada.alkemy.alkemy.models.response.MoviesResponse;
import ar.com.ada.alkemy.alkemy.services.*;

@RestController
public class PeliculaController {

    @Autowired
    PeliculaService service;

    @Autowired
    GeneroService generoService;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/movies")
    public ResponseEntity<GenericResponse> postPeliculas(@RequestBody InfoPeliculaNueva 
        infoPeliculaNueva, Integer id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuario = usuarioService.buscarPorUsername(username);

        Pelicula pelicula = service.crearPelicula(infoPeliculaNueva.generoId, infoPeliculaNueva.calificacion,
                infoPeliculaNueva.fechaCreacion, infoPeliculaNueva.imagen, infoPeliculaNueva.titulo);

        GenericResponse respuesta = new GenericResponse();
        respuesta.isOk = true;
        respuesta.id = pelicula.getPeliculaId(); 
        respuesta.mensaje = "La película ha sido creada con exito.";

        return ResponseEntity.ok(respuesta);

    }

    @GetMapping("/movies")
    public ResponseEntity<List<MoviesResponse>> getPeliculas() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuario = usuarioService.buscarPorUsername(username);

        return ResponseEntity.ok(service.getPeliculas());
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<?> getPeliculaById(@PathVariable Integer id) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuario = usuarioService.buscarPorUsername(username);
        
        if (service.buscarPorPeliculaId(id) == null) {
            GenericResponse respuesta = new GenericResponse();
            respuesta.mensaje = "El id ingresado no existe";
            return ResponseEntity.badRequest().body(respuesta);
        } else {
            return ResponseEntity.ok(service.buscarPorPeliculaId(id));
        }
    }


    @PutMapping("/movies/{id}")
    public ResponseEntity<GenericResponse> putPeliculas(@PathVariable Integer id,
            @RequestBody InfoPeliculaActualizada peliculaActualizada) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuario = usuarioService.buscarPorUsername(username);

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
