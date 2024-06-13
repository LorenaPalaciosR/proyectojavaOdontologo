package dh.backend.clinicamvc.service.impl;

import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.repository.IOdontologoRepository;
import dh.backend.clinicamvc.service.IOdontologoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService implements IOdontologoService {

    private IOdontologoRepository odontologoRepository;

    public OdontologoService(IOdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    public Odontologo agregarOdontologo(Odontologo odontologo){
        return odontologoRepository.save(odontologo);
    }

    public Optional<Odontologo> buscarUnOdontologo(Integer id){
        return odontologoRepository.findById(id);
    }
    public List<Odontologo> buscarTodosOdontologos(){
        return odontologoRepository.findAll();
    }

    @Override
    public void modificarOdontologo(Odontologo odontologo) {
        odontologoRepository.save(odontologo);
    }

    @Override
    public void modificarOdontologoId(Integer id, String nuevoNombre, String nuevoApellido, String nuevaMatricula) {
        Optional<Odontologo> odontologoOpt = odontologoRepository.findById(id);

        if (odontologoOpt.isPresent()) {
            Odontologo odontologo = odontologoOpt.get();

            // Modificar los atributos según los parámetros recibidos
            odontologo.setNombre(nuevoNombre);
            odontologo.setApellido(nuevoApellido);
            odontologo.setNroMatricula(nuevaMatricula);

            // Guardar los cambios en la base de datos
            odontologoRepository.save(odontologo);
        } else {
            throw new RuntimeException("Odontólogo no encontrado con ID: " + id);
        }
    }

   @Override
    public void eliminarOdontologo(Integer id) {
        odontologoRepository.deleteById(id);
    }

    @Override
    public List<Odontologo> buscarPorApellido(String apellido) {
        return odontologoRepository.buscarPorApellido(apellido);
    }

    @Override
    public List<Odontologo> buscarPorNombre(String nombre) {
        return odontologoRepository.findByNombreLike(nombre);
    }
}
