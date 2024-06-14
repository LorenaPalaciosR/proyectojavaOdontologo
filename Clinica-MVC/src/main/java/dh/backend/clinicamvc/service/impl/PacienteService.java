package dh.backend.clinicamvc.service.impl;

import dh.backend.clinicamvc.entity.Paciente;
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

    public Paciente registrarPaciente(Paciente paciente){
        LOGGER.info("Paciente Creado: " + paciente.getNombre() + " " + paciente.getApellido());
        return pacienteRepository.save(paciente);
    }

    public Optional<Paciente> buscarPorId(Integer id){
        LOGGER.info("Paciente Encontrado");
        return pacienteRepository.findById(id);
    }

    public List<Paciente> buscarTodos(){
        return pacienteRepository.findAll();
    }

    @Override
    public void actualizarPaciente(Paciente paciente) {
        LOGGER.info("Paciente Actualizado: " + paciente.getNombre() + " " + paciente.getApellido());
        pacienteRepository.save(paciente);
    }

    @Override
    public void eliminarPaciente(Integer id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteOptional = buscarPorId(id);
        if(pacienteOptional.isPresent()) {
            LOGGER.info("Paciente Actualizado");
            pacienteRepository.deleteById(id);
        }else
            throw new ResourceNotFoundException("{\"mensaje\":\"paciente no encontrado\"}");
    }
}
