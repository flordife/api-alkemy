package ar.com.ada.alkemy.alkemy.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ar.com.ada.alkemy.alkemy.entities.*;
import ar.com.ada.alkemy.alkemy.models.request.InfoPersonajeNuevo;
import ar.com.ada.alkemy.alkemy.models.response.*;
import ar.com.ada.alkemy.alkemy.services.*;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class PersonajeController {

    @Autowired
    PersonajeService service;

    @Autowired
    PeliculaService peliculaService;

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/characters")
    public ResponseEntity<List<CharactersResponse>> getPersonajes(String imagen, String nombre) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuario = usuarioService.buscarPorUsername(username);

        List<CharactersResponse> personajes = service.getPersonajes(imagen, nombre);

        return ResponseEntity.ok(personajes);
    }

    @PostMapping("/characters")
    public ResponseEntity<GenericResponse> postPersonaje(@RequestBody InfoPersonajeNuevo personajeNuevo) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuario = usuarioService.buscarPorUsername(username);

        GenericResponse respuesta = new GenericResponse();
        Personaje personaje = service.crearPersonaje(personajeNuevo.edad, personajeNuevo.historia,
                personajeNuevo.imagen, personajeNuevo.nombre, personajeNuevo.peso, personajeNuevo.peliculaId);

        respuesta.id = personaje.getPersonajeId();
        respuesta.isOk = true;
        respuesta.mensaje = "El personaje ha sido creado con éxito.";

        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/characters/{id}")
    public ResponseEntity<GenericResponse> putPersonaje(@PathVariable Integer id,
            @RequestBody InfoPersonajeNuevo personajeActualizado) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuario = usuarioService.buscarPorUsername(username);

        GenericResponse respuesta = new GenericResponse();

        if (service.buscarPersonajePorId(id) == null) {
            
            respuesta.isOk = false;
            respuesta.mensaje = "El id ingresado no existe";
            return ResponseEntity.badRequest().body(respuesta);
            
        } else {
            
            Personaje personaje = service.modificarPersonaje(id, personajeActualizado.peliculaId, personajeActualizado.edad,
                personajeActualizado.historia, personajeActualizado.imagen, personajeActualizado.peso);
            respuesta.id = personaje.getPersonajeId();
            respuesta.isOk = true;
            respuesta.mensaje = "El personaje ha sido actualizado";
            return ResponseEntity.ok(respuesta);
            
        }

    }

    @DeleteMapping("/characters/{id}")
    public ResponseEntity<GenericResponse> deletePersonaje(@PathVariable Integer id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuario = usuarioService.buscarPorUsername(username);

        GenericResponse respuesta = new GenericResponse();

        if (service.buscarPersonajePorId(id) == null) {
            respuesta.isOk = false;
            respuesta.mensaje = "El id ingresado no existe";
            return ResponseEntity.badRequest().body(respuesta);

        } else {

            service.borrarPersonajePorId(id);
            respuesta.isOk = true;
            respuesta.mensaje = "El personaje ha sido eliminado.";
            return ResponseEntity.ok(respuesta);

        }
    }

    @GetMapping("/characters/{id}")
    public ResponseEntity<?> getPersonajePorId(@PathVariable Integer id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuario = usuarioService.buscarPorUsername(username);

        if (service.buscarPersonajePorId(id) == null) {
            GenericResponse respuesta = new GenericResponse();
            respuesta.mensaje = "El id ingresado no existe";
            return ResponseEntity.badRequest().body(respuesta);
        } else {
            return ResponseEntity.ok(service.buscarPersonajePorId(id));
        }   
    }

    @GetMapping("characters?name=nombre")
    public ResponseEntity<Personaje> getPersonajePorNombre(@PathVariable String nombre) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuario = usuarioService.buscarPorUsername(username);

        return ResponseEntity.ok(service.getPersonaje(nombre));
    }

    @GetMapping("/characters?age=edad")
    public ResponseEntity<Personaje> getPersonajePorEdad(@PathVariable Integer edad) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuario = usuarioService.buscarPorUsername(username);

        return ResponseEntity.ok(service.getPersonaje(edad));
    }

    @GetMapping("/characters?movies=idMovie")
    public ResponseEntity<List<Personaje>> getPersonajesPorIdPelicula(@PathVariable Integer idMovie) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuario = usuarioService.buscarPorUsername(username);

        List<Personaje> personajes = service.traerPersonajesPorPelicula(idMovie);

        return ResponseEntity.ok(personajes);
    }




}
