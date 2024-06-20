package dh.backend.clinicamvc.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import dh.backend.clinicamvc.entity.Odontologo;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OdontologoServiceTest {
    private static Logger LOGGER = LoggerFactory.getLogger(OdontologoServiceTest.class);
    @Autowired
    //Inyectar dependencias
    private OdontologoService odontologoService;
    private Odontologo odontologo; //generamos odontólogo para usarlo

    @BeforeEach
    void setUp(){
        odontologo = new Odontologo();
        odontologo.setNombre("Lorena");
        odontologo.setApellido("Cosme");
        odontologo.setNroMatricula("123");
    }


    @Test
    @DisplayName("Testear que un odontologo fue creado")
    void testOdontologoGuardado() throws BadRequestException {
        Odontologo odontologoDesdeLaBD = odontologoService.agregarOdontologo(odontologo);

        assertNotNull(odontologoDesdeLaBD);
        assertNotNull(odontologoDesdeLaBD.getId());
    }

    @Test
    @DisplayName("Testear busqueda odontologo por id")
    void testOdontologoPorId() throws BadRequestException {
        // Primero guarda el odontólogo
        Odontologo odontologoGuardado = odontologoService.agregarOdontologo(odontologo);
        Integer id = odontologoGuardado.getId();

        // Luego se busca por ID
        Optional<Odontologo> odontologoEncontrado = odontologoService.buscarUnOdontologo(id);

        assertTrue(odontologoEncontrado.isPresent(), "El odontólogo debería estar presente en la base de datos");

        Odontologo odontologo1 = odontologoEncontrado.get();
        assertEquals(id, odontologo1.getId());
    }

    @Test
    @DisplayName("Testear busqueda todos los odontólogos")
    void testBusquedaTodos() {

        List<Odontologo> odontologos = odontologoService.buscarTodosOdontologos();

        assertTrue(odontologos.size()!=0);
    }

    @Test
    @DisplayName("Testear eliminar odontólogo por id")
    void testEliminarOdontologoPorId() throws ResourceNotFoundException, BadRequestException {

        // Primero guarda el odontólogo
        Odontologo odontologoGuardado = odontologoService.agregarOdontologo(odontologo);
        Integer id = odontologoGuardado.getId();

        Optional<Odontologo> odontologoAEliminar = odontologoService.eliminarOdontologo(id);
        Odontologo odontologo1 = odontologoAEliminar.get();
        List<Odontologo> odontologos2 = odontologoService.buscarTodosOdontologos();
        assertTrue(odontologos2.size()!=0);
    }



}