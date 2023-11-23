package ar.com.mq.expedientes.api.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ar.com.mq.expedientes.api.model.entity.Pase;

public interface PaseRepository extends JpaRepository<Pase, Long> , JpaSpecificationExecutor<Pase> {

}
