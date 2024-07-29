package ar.com.mq.expedientes.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import ar.com.mq.expedientes.api.model.dto.TipoExpedienteDTO;
import ar.com.mq.expedientes.api.model.entity.TipoExpediente;
import ar.com.mq.expedientes.api.model.mapper.interfaces.TipoExpedienteMapper;
import ar.com.mq.expedientes.api.service.interfaces.TipoExpedienteService;
import ar.com.mq.expedientes.api.service.repository.TipoExpedienteRepository;

@Service
public class TipoExpedienteServiceImpl implements TipoExpedienteService {

	private final TipoExpedienteMapper tipoExpedienteMapper;
	private final TipoExpedienteRepository tipoExpedienteRepository;

	@Autowired
	public TipoExpedienteServiceImpl(TipoExpedienteMapper tipoExpedienteMapper,
			TipoExpedienteRepository tipoExpedienteRepository) {
		super();
		this.tipoExpedienteMapper = tipoExpedienteMapper;
		this.tipoExpedienteRepository = tipoExpedienteRepository;
	}

	@Override
	public List<TipoExpedienteDTO> findAll(int page, int size, String search, String orderBy, String orientation) {
		PageRequest pageRequest = PageRequest.of(page, size);

		Page<TipoExpediente> tipoDocumentoPage = tipoExpedienteRepository.findAll(new Specification<TipoExpediente>() {

			@Override
			public Predicate toPredicate(Root<TipoExpediente> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();

				if (StringUtils.isNotBlank(search)) {
					predicates.add(cb.like(cb.lower(root.get("descripcion")), "%" + search.toLowerCase() + "%"));
				}

				// Ordenamos
				if (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(orientation)) {

					cq.orderBy(orientation.equals("asc") ? cb.asc(root.get(orderBy)) : cb.desc(root.get(orderBy)));

				} else {
					cq.orderBy(cb.asc(root.get("descripcion")));
				}
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}

		}, pageRequest);

		return this.tipoExpedienteMapper.toListDTO(tipoDocumentoPage.getContent());
	}

}
