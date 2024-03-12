package ar.com.mq.expedientes.api.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ar.com.mq.expedientes.api.model.entity.Documento;

@Repository
public interface DocumentRepository extends JpaRepository<Documento, Long>, JpaSpecificationExecutor<Documento> {

	Documento findByNombre(String nombre);

	List<Documento> findAllByExpedienteId(Long expedienteId);
}
