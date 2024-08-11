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
import org.springframework.transaction.annotation.Transactional;

import ar.com.mq.expedientes.api.model.dto.TipoExpedienteDTO;
import ar.com.mq.expedientes.api.model.entity.TipoExpediente;
import ar.com.mq.expedientes.api.model.mapper.interfaces.TipoExpedienteMapper;
import ar.com.mq.expedientes.api.service.interfaces.TipoExpedienteService;
import ar.com.mq.expedientes.api.service.repository.TipoExpedienteRepository;
import ar.com.mq.expedientes.core.exception.exceptions.MunicipalidadMQRuntimeException;

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

	@Override
	public TipoExpedienteDTO create(TipoExpedienteDTO expediente) {
		if (expediente == null || expediente.getDescripcion().isBlank() || expediente.getDescripcion().isEmpty()) {
			throw MunicipalidadMQRuntimeException.badRequestException("El campo descripción es obligatorio.");
		}

		var tipoExpediente = this.tipoExpedienteRepository.findByDescripcionIgnoreCase(expediente.getDescripcion());

		if (tipoExpediente != null) {
			throw MunicipalidadMQRuntimeException
					.conflictException("Ya existe un tipo de expediente registrado con esa descripción");
		}

		var tipoExpedienteSaved = this.tipoExpedienteRepository.save(this.tipoExpedienteMapper.toEntity(expediente));

		return tipoExpedienteMapper.toDTO(tipoExpedienteSaved);
	}

	@Override
	public TipoExpediente findById(Integer expedienteId) {
		return this.tipoExpedienteRepository.findById(expedienteId).orElseThrow(() -> MunicipalidadMQRuntimeException
				.notFoundException("No se encontro tipo de expediente con el identificador pasado como parámetro."));
	}

	@Override
	@Transactional
	public TipoExpedienteDTO update(TipoExpedienteDTO expediente, Integer expedienteId) {
		if (expedienteId == null) {
			throw MunicipalidadMQRuntimeException.badRequestException("El identificador del expediente es obligatorio");
		}

		var tipoExpediente = findById(expedienteId);

		tipoExpediente.setDescripcion(expediente.getDescripcion());

		this.tipoExpedienteRepository.save(tipoExpediente);

		return this.tipoExpedienteMapper.toDTO(tipoExpediente);

	}

	@Override
	public void delete(Integer expedienteId) {
		if (expedienteId == null) {
			throw MunicipalidadMQRuntimeException.badRequestException("El identificador del expediente es obligatorio");
		}

		var tipoExpediente = findById(expedienteId);

		this.tipoExpedienteRepository.delete(tipoExpediente);
	}

}
