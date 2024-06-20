package dh.backend.clinicamvc.service;


import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IOdontologoService {
    Odontologo agregarOdontologo(Odontologo odontologo);

    Optional<Odontologo> buscarUnOdontologo(Integer id);
    List<Odontologo> buscarTodosOdontologos();

    void modificarOdontologo(Odontologo odontologo);

    void modificarOdontologoId(Integer id, String nuevoNombre, String nuevoApellido, String nuevaMatricula);
    Optional<Odontologo> eliminarOdontologo(Integer id) throws ResourceNotFoundException;

    //Métodos con HQL

    List<Odontologo> buscarPorApellido(String apellido);
    List<Odontologo> buscarPorNombre(String nombre);

}
