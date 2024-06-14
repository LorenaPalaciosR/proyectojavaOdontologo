package dh.backend.clinicamvc.repository;

import dh.backend.clinicamvc.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IOdontologoRepository extends JpaRepository<Odontologo, Integer> {
    //Buscar odontologos por apellido
    @Query("Select o from Odontologo o where LOWER(o.apellido) = LOWER(:apellido)") //HQL
    //Devuelve la query:
    List<Odontologo> buscarPorApellido(@Param("apellido") String apellido);

    @Query("Select o from Odontologo o where LOWER(o.nombre) LIKE LOWER(CONCAT('%',:nombre, '%'))") //HQL
    List<Odontologo> findByNombreLike(@Param("nombre") String nombre);


}
