package ar.com.mq.expedientes.api.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.com.mq.expedientes.api.model.entity.Pase;

public interface PaseRepository extends JpaRepository<Pase, Long>, JpaSpecificationExecutor<Pase> {

	@Query("SELECT p FROM Pase p WHERE p.expediente.id = :expedienteId ORDER BY p.id DESC")
	List<Pase> buscarUltimoPaseDeExpediente(@Param("expedienteId") Long expedienteId);

}
