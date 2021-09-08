package ar.com.ada.alkemy.alkemy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ar.com.ada.alkemy.alkemy.entities.Genero;
import ar.com.ada.alkemy.alkemy.models.response.GenericResponse;
import ar.com.ada.alkemy.alkemy.services.GeneroService;

@RestController
public class GeneroController {

    @Autowired
    GeneroService service;
    
    @PostMapping("/generos")
    public ResponseEntity<GenericResponse> postGenero(@RequestBody Genero genero){

        service.crearGenero(genero);
        GenericResponse respuesta = new GenericResponse();
        respuesta.isOk = true;
        respuesta.id = genero.getGeneroId();
        respuesta.mensaje = "El género ha sido creado con éxito.";
        
        return ResponseEntity.ok(respuesta);
    }
}
