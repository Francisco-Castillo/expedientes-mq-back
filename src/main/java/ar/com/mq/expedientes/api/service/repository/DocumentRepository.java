package ar.com.mq.expedientes.api.service.repository;

import ar.com.mq.expedientes.api.model.entity.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Documento, Long>, JpaSpecificationExecutor<Documento> {
}
