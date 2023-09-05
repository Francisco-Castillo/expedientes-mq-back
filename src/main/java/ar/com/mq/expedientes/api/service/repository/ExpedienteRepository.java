package ar.com.mq.expedientes.api.service.repository;

import ar.com.mq.expedientes.api.model.entity.Expediente;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ExpedienteRepository extends JpaRepository<Expediente, Long>, JpaSpecificationExecutor<Expediente> {
}
