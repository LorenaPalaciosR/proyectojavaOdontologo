package dh.backend.clinicamvc.service.impl;
import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.exception.BadRequestException;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;
import dh.backend.clinicamvc.repository.IOdontologoRepository;
import dh.backend.clinicamvc.service.IOdontologoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class OdontologoService implements IOdontologoService {

    private static Logger LOGGER = Logger.getLogger(String.valueOf(OdontologoService.class));
    private IOdontologoRepository odontologoRepository;

    public OdontologoService(IOdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    public Odontologo agregarOdontologo(Odontologo odontologo) throws BadRequestException {
        if(odontologo.getNombre().isEmpty() && odontologo.getApellido().isEmpty() && odontologo.getNroMatricula().isEmpty()){
            throw new BadRequestException("{\"mensaje\":\"odontologo no se pudo crear\"}");
        } else{
            LOGGER.info("Odontologo Creado: " + odontologo.getNombre() + " " + odontologo.getApellido());
            return odontologoRepository.save(odontologo);
        }


    }

    public Optional<Odontologo> buscarUnOdontologo(Integer id){
        LOGGER.info("¡El odontologo fue encontrado! ");
        return odontologoRepository.findById(id);
    }
    public List<Odontologo> buscarTodosOdontologos(){
        LOGGER.info("Lista de todos los odontólogos");
        return odontologoRepository.findAll();
    }

    @Override
    public void modificarOdontologo(Odontologo odontologo) {
        LOGGER.info("Odontologo Actualizado: " + odontologo.getNombre() + " " + odontologo.getApellido() + " con matrícula:  " + odontologo.getNroMatricula());
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
            LOGGER.info("Odontologo Actualizado: " + odontologo.getNombre() + " " + odontologo.getApellido() + " con matrícula:  " + odontologo.getNroMatricula());
            odontologoRepository.save(odontologo);
        } else {
            throw new RuntimeException("Odontólogo no encontrado con ID: " + id);
        }
    }

   @Override
    public Optional<Odontologo> eliminarOdontologo(Integer id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoOptional = odontologoRepository.findById(id);
        if(odontologoOptional.isPresent()) {
            LOGGER.info("Odontologo Eliminado");
            odontologoRepository.deleteById(id);
        }else
            throw new ResourceNotFoundException("{\"mensaje\":\"odontologo no encontrado\"}");
       return odontologoOptional;
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
