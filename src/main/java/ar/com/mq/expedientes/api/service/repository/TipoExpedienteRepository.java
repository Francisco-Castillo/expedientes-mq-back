package ar.com.mq.expedientes.api.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ar.com.mq.expedientes.api.model.entity.TipoExpediente;

@Repository
public interface TipoExpedienteRepository
		extends JpaRepository<TipoExpediente, Integer>, JpaSpecificationExecutor<TipoExpediente> {

}
