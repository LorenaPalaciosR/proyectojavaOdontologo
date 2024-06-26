package dh.backend.clinicamvc.controller;

import dh.backend.clinicamvc.Dto.request.TurnoRequestDto;
import dh.backend.clinicamvc.Dto.response.TurnoResponseDto;
import dh.backend.clinicamvc.exception.BadRequestException;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;
import dh.backend.clinicamvc.service.ITurnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/turno")
public class TurnoController {
    private ITurnoService turnoService;

    public TurnoController(ITurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @PostMapping
    public ResponseEntity<TurnoResponseDto> agregarTurno(@RequestBody TurnoRequestDto turno) throws BadRequestException {
        TurnoResponseDto turnoADevolver = turnoService.registrar(turno);
        return ResponseEntity.status(HttpStatus.CREATED).body(turnoADevolver);
    }
    @GetMapping
    public ResponseEntity<List<TurnoResponseDto>> buscarTodosTurnos(){
        return ResponseEntity.ok(turnoService.buscarTodos());
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> modificarTurno(@PathVariable Integer id, @RequestBody TurnoRequestDto turno) throws BadRequestException {
        turnoService.actualizarTurno(id, turno);
        return ResponseEntity.ok("Turno modificado");
    }

    //formatear fechas
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @GetMapping("/fechas")
    public ResponseEntity<List<TurnoResponseDto>> buscarEntreFechas(@RequestParam String inicio,@RequestParam String fin){
       LocalDate fechaInicio = LocalDate.parse(inicio);
       LocalDate fechaFinal = LocalDate.parse(fin);
       return ResponseEntity.ok(turnoService.buscarTurnoEntreFechas(fechaInicio,fechaFinal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Integer id) throws ResourceNotFoundException, BadRequestException {
        turnoService.eliminarTurno(id);
        return ResponseEntity.ok("\"{\"mensaje\":\"turno eliminado\"}\"");
    }
}
