package dh.backend.clinicamvc.service.impl;

import dh.backend.clinicamvc.Dto.request.TurnoRequestDto;
import dh.backend.clinicamvc.Dto.response.TurnoResponseDto;
import dh.backend.clinicamvc.entity.Domicilio;
import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.entity.Turno;
import dh.backend.clinicamvc.exception.BadRequestException;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TurnoServiceTest {
    private static Logger LOGGER = LoggerFactory.getLogger(TurnoServiceTest.class);
    @Autowired
    private TurnoService turnoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private OdontologoService odontologoService;

    private Odontologo odontologo;
    private Paciente paciente;
    private TurnoRequestDto turnoRequestDto;

    @BeforeEach
    void setUp1() throws BadRequestException {
        odontologo = new Odontologo();
        odontologo.setNombre("Lorena");
        odontologo.setApellido("Cosme");
        odontologo.setNroMatricula("123");

        Odontologo odontologoGuardado = odontologoService.agregarOdontologo(odontologo);
        Integer idOdontologo = odontologoGuardado.getId();

        paciente = new Paciente();
        paciente.setNombre("Menganito");
        paciente.setApellido("Cosme");
        paciente.setDni("464646");
        paciente.setFechaIngreso(LocalDate.of(2024,01,12));
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Calle falsa");
        domicilio.setNumero(123);
        domicilio.setLocalidad("San Pedro");
        domicilio.setProvincia("Jujuy");
        paciente.setDomicilio(domicilio);

        Paciente pacienteGuardado = pacienteService.registrarPaciente(paciente);
        Integer idPaciente = pacienteGuardado.getId();

        turnoRequestDto = new TurnoRequestDto();
        turnoRequestDto.setOdontologo_id(idOdontologo);
        turnoRequestDto.setPaciente_id(idPaciente);
        turnoRequestDto.setFecha("2024-06-30");
        turnoRequestDto.setHora("14:30:00.00");
    }

    @Test
    @DisplayName("Testear que un turno fue guardado")
    void testTurnoGuardado() throws BadRequestException {
        TurnoResponseDto turnoDesdeLaBD = turnoService.registrar(turnoRequestDto);

        assertNotNull(turnoDesdeLaBD);
        assertNotNull(turnoDesdeLaBD.getId());
    }

    @Test
    @DisplayName("Testear busqueda turno por id")
    void testBuscarTurnoPorId() throws BadRequestException {
        // Primero guarda el turno
        TurnoResponseDto turnoDesdeLaBD = turnoService.registrar(turnoRequestDto);
        Integer id = turnoDesdeLaBD.getId();

        // Luego se busca por ID
        TurnoResponseDto turnoEncontrado = turnoService.buscarPorId(id);
        assertNotNull(turnoEncontrado, "El turno debería estar presente en la base de datos");

        assertEquals(id, turnoEncontrado.getId());
    }

    @Test
    @DisplayName("Testear busqueda todos los turnos")
    void testBusquedaTodosTurnos() throws BadRequestException {

        List<TurnoResponseDto> turnos = turnoService.buscarTodos();

        assertTrue(turnos.size()!=0);
    }

    @Test
    @DisplayName("Testear eliminar turno por id")
    void testEliminarTurnoPorId() throws BadRequestException, ResourceNotFoundException {
        // Primero guarda el turno
        TurnoResponseDto turnoDesdeLaBD = turnoService.registrar(turnoRequestDto);
        Integer id = turnoDesdeLaBD.getId();

        // Luego se busca por ID
        TurnoResponseDto turnoAEliminar = turnoService.eliminarTurno(id);
        assertNotNull(turnoAEliminar, "El turno debería estar presente en la base de datos");

        assertEquals(id, turnoAEliminar.getId());
    }

}