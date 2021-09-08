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
    public ResponseEntity<List<CharactersResponse>> getPersonajes() {
        return ResponseEntity.ok(service.getPersonajes());
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
        Personaje personaje = service.modificarPersonaje(id, personajeActualizado.peliculaId, personajeActualizado.edad,
                personajeActualizado.historia, personajeActualizado.imagen, personajeActualizado.peso);

        respuesta.id = personaje.getPersonajeId();
        respuesta.isOk = true;
        respuesta.mensaje = "El personaje ha sido actualizado";

        return ResponseEntity.ok(respuesta);
    
    }

    @DeleteMapping("/characters/{id}") 
    public ResponseEntity<GenericResponse> deletePersonaje(@PathVariable Integer id){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuario = usuarioService.buscarPorUsername(username);

        GenericResponse respuesta = new GenericResponse();
        service.borrarPersonajePorId(id);

        respuesta.isOk = true;
        respuesta.mensaje = "El personaje ha sido eliminado.";

        return ResponseEntity.ok(respuesta);
    }


}
