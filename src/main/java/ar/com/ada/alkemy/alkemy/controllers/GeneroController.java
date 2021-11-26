package ar.com.ada.alkemy.alkemy.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ar.com.ada.alkemy.alkemy.entities.Genero;
import ar.com.ada.alkemy.alkemy.entities.Usuario;
import ar.com.ada.alkemy.alkemy.models.response.GenericResponse;
import ar.com.ada.alkemy.alkemy.services.GeneroService;
import ar.com.ada.alkemy.alkemy.services.UsuarioService;

@RestController
public class GeneroController {

    @Autowired
    GeneroService service;

    @Autowired
    UsuarioService usuarioService;
    
    @PostMapping("/generos")
    public ResponseEntity<GenericResponse> postGenero(@RequestBody Genero genero){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        usuarioService.buscarPorUsername(username);


        service.crearGenero(genero);
        GenericResponse respuesta = new GenericResponse();
        respuesta.isOk = true;
        respuesta.id = genero.getGeneroId();
        respuesta.mensaje = "El género ha sido creado con éxito.";
        
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/generos")
    public ResponseEntity<List<Genero>> getGeneros() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        usuarioService.buscarPorUsername(username);

        return ResponseEntity.ok(service.traerTodos());
    }

    @GetMapping("/generos/{nombre}") 
    public ResponseEntity<Genero> getGeneroByNombre (@PathVariable String nombre) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        usuarioService.buscarPorUsername(username);

        return ResponseEntity.ok(service.buscarPorNombre(nombre));
    }
}
