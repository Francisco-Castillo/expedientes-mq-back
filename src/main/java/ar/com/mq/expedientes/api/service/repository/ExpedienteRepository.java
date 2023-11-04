package ar.com.mq.expedientes.api.service.repository;

import ar.com.mq.expedientes.api.model.entity.Expediente;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ExpedienteRepository extends JpaRepository<Expediente, Long>, JpaSpecificationExecutor<Expediente> {

    Expediente findByNumero(String numero);

    @Transactional
    @Modifying
    @Query("UPDATE Expediente e SET e.estado = :status where e.id=:expedienteId")
    void updateStatus(Long expedienteId, String status);
}
