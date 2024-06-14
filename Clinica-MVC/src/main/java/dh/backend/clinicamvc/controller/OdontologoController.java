package dh.backend.clinicamvc.controller;

import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;
import dh.backend.clinicamvc.service.impl.OdontologoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {
    private OdontologoService odontologoService;

    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }
    @PostMapping
    public ResponseEntity<Odontologo> registrarOdontologo(@RequestBody Odontologo odontologo){
        return ResponseEntity.status(HttpStatus.CREATED).body(odontologoService.agregarOdontologo(odontologo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Odontologo>  buscarOdontologoPorId(@PathVariable Integer id) {
        Optional<Odontologo> odontologo = odontologoService.buscarUnOdontologo(id);
        if (odontologo.isPresent()){
            Odontologo odontologoARetornar = odontologo.get();
            return ResponseEntity.ok(odontologoARetornar);
        }
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping
    public ResponseEntity<String> modificarOdontologo(@RequestBody Odontologo odontologo){
        Optional<Odontologo> odontologoOptional = odontologoService.buscarUnOdontologo(odontologo.getId());
        if(odontologoOptional.isPresent()){
            odontologoService.modificarOdontologo(odontologo);
            return ResponseEntity.ok("{\"message\": \"odontologo modificado\"}");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificarOdontologoPorId(@PathVariable Integer id, @RequestBody Odontologo odontologoDto) {
        try {
            odontologoService.modificarOdontologoId(id, odontologoDto.getNombre(), odontologoDto.getApellido(), odontologoDto.getNroMatricula());
            return ResponseEntity.ok("{\"message\": \"odontologo actulizado con exito\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar el odontólogo: " + e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Integer id) throws ResourceNotFoundException {
            odontologoService.eliminarOdontologo(id);
            return ResponseEntity.ok("{\"mensaje\":\"odontologo actualizado\"}");

    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> buscarTodos(){
        return ResponseEntity.ok(odontologoService.buscarTodosOdontologos());
    }

    @GetMapping("/apellido/{apellido}")
    public ResponseEntity<List<Odontologo>> buscarPorApellido(@PathVariable String apellido){
        List<Odontologo> listaOndontologos = odontologoService.buscarPorApellido(apellido);
        if(listaOndontologos.size()>0){
            return ResponseEntity.ok(listaOndontologos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Odontologo>> buscarTodos(@PathVariable String nombre){
        return ResponseEntity.ok(odontologoService.buscarPorNombre(nombre));
    }
}
