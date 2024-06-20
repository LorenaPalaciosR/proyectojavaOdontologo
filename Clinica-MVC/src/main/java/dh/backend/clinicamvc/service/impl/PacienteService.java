package dh.backend.clinicamvc.service.impl;

import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.exception.BadRequestException;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;
import dh.backend.clinicamvc.repository.IPacienteRepository;
import dh.backend.clinicamvc.service.IPacienteService;
import org.springframework.stereotype.Service;

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
        LOGGER.info("Paciente Creado: " + paciente.getNombre() + " " + paciente.getApellido());
        return pacienteRepository.save(paciente);
    }

    public Optional<Paciente> buscarPorId(Integer id) throws BadRequestException {
        Optional<Paciente> devolverPacente = pacienteRepository.findById(id);
        if(devolverPacente.isEmpty()){
            throw new BadRequestException("{\"mensaje\":\"paciente no encontrado\"}");
        } else {
            LOGGER.info("Paciente Encontrado");
            return devolverPacente;
        }
    }

    public List<Paciente> buscarTodos(){
        LOGGER.info("Lista de todos los pacientes");
        return pacienteRepository.findAll();
    }

    @Override
    public void actualizarPaciente(Paciente paciente) throws BadRequestException {
        LOGGER.info("Paciente Actualizado: " + paciente.getNombre() + " " + paciente.getApellido());
        pacienteRepository.save(paciente);
    }

    @Override
    public Optional<Paciente> eliminarPaciente(Integer id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(id);
        if(pacienteOptional.isPresent()) {
            LOGGER.info("Paciente Eliminado");
            pacienteRepository.deleteById(id);
            return pacienteOptional;
        }else
            throw new ResourceNotFoundException("{\"mensaje\":\"paciente no encontrado\"}");

    }
}
