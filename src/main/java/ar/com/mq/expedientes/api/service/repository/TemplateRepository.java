package ar.com.mq.expedientes.api.service.repository;

import ar.com.mq.expedientes.api.model.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {
    Template findByTipo(String tipo);
}
