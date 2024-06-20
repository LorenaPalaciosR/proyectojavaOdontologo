package dh.backend.clinicamvc.service.impl;


import dh.backend.clinicamvc.entity.Domicilio;
import dh.backend.clinicamvc.entity.Paciente;

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

@SpringBootTest
class PacienteServiceTest {
    private static Logger LOGGER = LoggerFactory.getLogger(PacienteServiceTest.class);
    @Autowired
    //Inyectar dependencias
    private PacienteService pacienteService;
    private Paciente paciente; //generamos paciente para usarlo

    @BeforeEach
    void setUp(){
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
    }


    @Test
    @DisplayName("Testear que un paciente fue guardado")
    void testPacienteGuardado() throws BadRequestException {
        Paciente pacienteDesdeLaBD = pacienteService.registrarPaciente(paciente);
        assertNotNull(pacienteDesdeLaBD);
    }

    @Test
    @DisplayName("Testear busqueda paciente por id")
    void testPacientePorId() throws BadRequestException {
        Paciente pacienteGuardado = pacienteService.registrarPaciente(paciente);
        Integer id = pacienteGuardado.getId();
        Optional<Paciente> pacienteEncontrado = pacienteService.buscarPorId(id);
        assertTrue(pacienteEncontrado.isPresent(), "El paciente debería estar presente en la base de datos");
        assertEquals(id, pacienteGuardado.getId());
    }

    @Test
    @DisplayName("Testear busqueda todos los pacientes")
    void testBusquedaTodos() {
        List<Paciente> pacientes = pacienteService.buscarTodos();
        assertTrue(pacientes.size()!=0);
    }

    @Test
    @DisplayName("Testear eliminar paciente por id")
    void testEliminarPacientePorId() throws ResourceNotFoundException, BadRequestException {
        // Crea un nuevo paciente
        Paciente paciente = new Paciente();
        paciente.setNombre("Juan");
        paciente.setApellido("Perez");
        // Registra el nuevo paciente y obtén su ID
        Paciente pacienteGuardado = pacienteService.registrarPaciente(paciente);
        Integer id = pacienteGuardado.getId();
        // Asegúrate de que el paciente se ha guardado correctamente
        assertNotNull(id, "El paciente no se guardó correctamente, el ID es nulo");
        // Elimina el paciente recién creado usando su ID
        Optional<Paciente> pacienteAEliminar = pacienteService.eliminarPaciente(id);
        // Verifica que el paciente ha sido eliminado
        List<Paciente> pacientesRestantes = pacienteService.buscarTodos();
        assertTrue(pacientesRestantes.stream().noneMatch(p -> p.getId().equals(id)), "El paciente no fue eliminado correctamente");
        // Opcional: Verifica que el paciente eliminado sea el correcto
        assertTrue(pacienteAEliminar.isPresent(), "El paciente a eliminar no fue encontrado");
        assertEquals(pacienteGuardado.getId(), pacienteAEliminar.get().getId(), "El ID del paciente eliminado no coincide con el esperado");
    }


}