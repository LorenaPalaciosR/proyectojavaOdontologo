package dh.backend.clinicamvc.service.impl;

import dh.backend.clinicamvc.Dto.response.TurnoResponseDto;
import dh.backend.clinicamvc.entity.Domicilio;
import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.entity.Turno;
import dh.backend.clinicamvc.exception.BadRequestException;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;
import dh.backend.clinicamvc.repository.IPacienteRepository;
import dh.backend.clinicamvc.service.IPacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class PacienteService implements IPacienteService {

    private static Logger LOGGER = Logger.getLogger(String.valueOf(PacienteService.class));

    private IPacienteRepository pacienteRepository;

    public PacienteService(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente registrarPaciente(Paciente paciente) throws BadRequestException {
        if (paciente != null) {
            LOGGER.info("Paciente Creado: " + paciente.getNombre() + " " + paciente.getApellido());
            return pacienteRepository.save(paciente);
        } else {
            throw new BadRequestException("{\"mensaje\":\"paciente no se pudo registrar\"}");
        }
    }

    public Optional<Paciente> buscarPorId(Integer id) {
        Optional<Paciente> devolverPacente = pacienteRepository.findById(id);
        LOGGER.info("Paciente Encontrado");
        return devolverPacente;
    }

    public List<Paciente> buscarTodos(){
        LOGGER.info("Lista de todos los pacientes");
        return pacienteRepository.findAll();
    }

    @Override
    public void actualizarPaciente(Paciente paciente){
        LOGGER.info("Paciente Actualizado: " + paciente.getNombre() + " " + paciente.getApellido());
        pacienteRepository.save(paciente);
    }

    @Override
    public Optional<Paciente> eliminarPaciente(Integer id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(id); // Aquí se corrige la llamada al repositorio directamente.
        if(pacienteOptional.isPresent()) {
            LOGGER.info("Paciente Eliminado: " + pacienteOptional.get().getNombre() + " " + pacienteOptional.get().getApellido());
            pacienteRepository.deleteById(id);
            return pacienteOptional;
        } else {
            LOGGER.warning("Paciente no encontrado con ID: " + id); // Se registra un mensaje de advertencia.
            throw new ResourceNotFoundException("{\"mensaje\":\"paciente no encontrado\"}");
        }
    }
}