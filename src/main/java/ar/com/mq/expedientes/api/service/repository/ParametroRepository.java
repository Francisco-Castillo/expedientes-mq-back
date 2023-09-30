package ar.com.mq.expedientes.api.service.repository;

import ar.com.mq.expedientes.api.model.entity.Parametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ParametroRepository extends JpaRepository<Parametro, Long> {

    @Query(value = "SELECT p.valor FROM Parametro p WHERE p.nombre =:nombre")
    String getValor(String nombre);

    Parametro findByNombre(String nombre);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Parametro p SET p.valor =:value WHERE p.nombre=:name")
    void updateValue(String name, String value);
}
