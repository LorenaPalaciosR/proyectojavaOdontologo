package dh.backend.clinicamvc.service.impl;

import dh.backend.clinicamvc.Dto.request.TurnoRequestDto;
import dh.backend.clinicamvc.Dto.response.OdontologoResponseDto;
import dh.backend.clinicamvc.Dto.response.PacienteResponseDto;
import dh.backend.clinicamvc.Dto.response.TurnoResponseDto;
import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.entity.Turno;
import dh.backend.clinicamvc.exception.BadRequestException;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;
import dh.backend.clinicamvc.repository.IOdontologoRepository;
import dh.backend.clinicamvc.repository.IPacienteRepository;
import dh.backend.clinicamvc.repository.ITurnoRepository;
import dh.backend.clinicamvc.service.ITurnoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@Service
public class TurnoService implements ITurnoService {

    private static Logger LOGGER = Logger.getLogger(String.valueOf(TurnoService.class));

    private IOdontologoRepository odontologoRepository;
    private IPacienteRepository pacienteRepository;
    private ITurnoRepository turnoRepository;
    private ModelMapper modelMapper;

    public TurnoService(IOdontologoRepository odontologoRepository, IPacienteRepository pacienteRepository, ITurnoRepository turnoRepository, ModelMapper modelMapper) {
        this.odontologoRepository = odontologoRepository;
        this.pacienteRepository = pacienteRepository;
        this.turnoRepository = turnoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TurnoResponseDto registrar(TurnoRequestDto turnoRequestDto) throws BadRequestException {
        Optional<Paciente> paciente = pacienteRepository.findById(turnoRequestDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologoRepository.findById(turnoRequestDto.getOdontologo_id());
        Turno turnoARegistrar = new Turno();
        Turno turnoGuardado = null;
        TurnoResponseDto turnoADevolver = null;
        if (paciente.isEmpty() || odontologo.isEmpty()) {
            throw new BadRequestException("{\"mensaje\":\"paciente u odontologo no existe\"}");
        } else {
            turnoARegistrar.setOdontologo(odontologo.get());
            turnoARegistrar.setPaciente(paciente.get());
            turnoARegistrar.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));
            turnoARegistrar.setHora(LocalTime.parse(turnoRequestDto.getHora()));
            turnoGuardado = turnoRepository.save(turnoARegistrar);
            turnoADevolver = mapToResponseDto(turnoGuardado);
            LOGGER.info("Turno asignado :" + turnoADevolver);
            return turnoADevolver;
        }

    }

    @Override
    public TurnoResponseDto buscarPorId(Integer id) {
        Optional<Turno> turnoOptional = turnoRepository.findById(id);
        Turno turnoEncontrado = turnoOptional.get();
        TurnoResponseDto turnoADevolver = mapToResponseDto(turnoEncontrado);
        LOGGER.info("Turno encontrado");
        return turnoADevolver;
    }


    @Override
    public List<TurnoResponseDto> buscarTodos() {
        List<Turno> turnos = turnoRepository.findAll();
        List<TurnoResponseDto> turnosADevolver = new ArrayList<>();
        TurnoResponseDto turnoAuxiliar = null;
        for(Turno turno: turnos){
            turnoAuxiliar = mapToResponseDto(turno);
            turnosADevolver.add(turnoAuxiliar);
        }
        return turnosADevolver;
    }

    @Override
    public Turno actualizarTurno(Integer id, TurnoRequestDto turnoRequestDto) throws BadRequestException {
        Optional<Paciente> paciente = pacienteRepository.findById(turnoRequestDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologoRepository.findById(turnoRequestDto.getOdontologo_id());
        Optional<Turno> turno = turnoRepository.findById(id);
        Turno turnoAModificar = new Turno();
        if(paciente.isEmpty() && odontologo.isEmpty() && turno.isEmpty()){
            throw new BadRequestException("{\"mensaje\":\"No se pudo actualizar el turno\"}");
        } else{
            turnoAModificar.setId(id);
            turnoAModificar.setOdontologo(odontologo.get());
            turnoAModificar.setPaciente(paciente.get());
            turnoAModificar.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));
            turnoAModificar.setHora(LocalTime.parse(turnoRequestDto.getHora()));
            LOGGER.info("Turno modificado :" + turnoAModificar);
            turnoRepository.save(turnoAModificar);
            return turnoAModificar;
        }
    }

    @Override
    public TurnoResponseDto eliminarTurno(Integer id) throws ResourceNotFoundException, BadRequestException {
        TurnoResponseDto turnoResponseDto = buscarPorId(id);
        if(turnoResponseDto!= null){
            LOGGER.info("Turno eliminado");
            turnoRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("\"{\"mensaje\":\"turno no encontrado\"}\"");
        }
        return turnoResponseDto;
    }

    @Override
    public List<TurnoResponseDto> buscarTurnoEntreFechas(LocalDate starDate, LocalDate endDate) {
        List<Turno> listadoTurnos= turnoRepository.buscarTurnoEntreFechas(starDate,endDate);
        List<TurnoResponseDto> ListadoARetornar = new ArrayList<>();
        TurnoResponseDto turnoAuxiliar = null;
        for (Turno turno:listadoTurnos){
            turnoAuxiliar = mapToResponseDto(turno);
            ListadoARetornar.add(turnoAuxiliar);
        }
        return ListadoARetornar;
    }


    // metodo que mapea turno en turnoResponseDto
    private TurnoResponseDto mapToResponseDto(Turno turno){
        TurnoResponseDto turnoResponseDto = modelMapper.map(turno, TurnoResponseDto.class);
        turnoResponseDto.setOdontologo(modelMapper.map(turno.getOdontologo(), OdontologoResponseDto.class));
        turnoResponseDto.setPaciente(modelMapper.map(turno.getPaciente(), PacienteResponseDto.class));
        return  turnoResponseDto;
    }
}

