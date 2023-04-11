package com.clientes.api.web.controller;

import com.clientes.api.domain.Personn;
import com.clientes.api.domain.service.PersonnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


//Con esto se le indica a spring que esta clase va a ser un controlador rest
@RestController
@RequestMapping("/persons")
public class PersonnController {

    @Autowired
    private PersonnService personnService;
    //Se van a implementar cada uno de los metodos de nuestro PersonnService

    //Se van a controlar las respuestas del api con Http (ResponseEntity)
    @GetMapping("/all")
    public ResponseEntity<List<Personn>> getAll(){

        return new ResponseEntity<>(personnService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/hospital/{idCenterPerson}")
    public ResponseEntity<List<Personn>> getByCenterUbi(@PathVariable("idCenterPerson") int idCenterPerson){
        return personnService.getByCenterUbi(idCenterPerson)
                .map(center -> new ResponseEntity(center, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    //Se utiliza la funcion .map ya que el metodo "getByIdPerson" llama a un Optional
    @GetMapping("/{idPersonn}")
    public ResponseEntity<Personn> getByIdPersonn(@PathVariable("idPersonn") int idPersonn){

        return personnService.getByIdPerson(idPersonn)
                .map(personn -> new ResponseEntity<>(personn, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping("/save")
    public ResponseEntity<Personn> save(@RequestBody Personn personn){

        return new ResponseEntity<>(personnService.save(personn), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete (@PathVariable("id") int idPersonn){

        if(personnService.delete(idPersonn)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
