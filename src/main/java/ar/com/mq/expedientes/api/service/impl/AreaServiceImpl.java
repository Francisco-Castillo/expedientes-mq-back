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

import ar.com.mq.expedientes.api.model.dto.AreaDTO;
import ar.com.mq.expedientes.api.model.entity.Area;
import ar.com.mq.expedientes.api.model.mapper.interfaces.AreaMapper;
import ar.com.mq.expedientes.api.service.interfaces.AreaService;
import ar.com.mq.expedientes.api.service.repository.AreaRepository;
import ar.com.mq.expedientes.core.exception.exceptions.MunicipalidadMQRuntimeException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AreaServiceImpl implements AreaService {

	private final AreaMapper areaMapper;
	private final AreaRepository areaRepository;

	@Autowired
	public AreaServiceImpl(AreaMapper areaMapper, AreaRepository areaRepository) {
		this.areaMapper = areaMapper;
		this.areaRepository = areaRepository;
	}

	@Override
	public void save(AreaDTO area) {
		log.debug("Por guardar area: {}", area);
		Area areaDeReferencia = null;
		Area entity = this.areaMapper.toEntity(area);

		if (area.getReferenciaId() != null) {

			areaDeReferencia = this.areaRepository.findById(area.getReferenciaId())
					.orElseThrow(() -> MunicipalidadMQRuntimeException.notFoundException(
							"No se encontró dependencia con el identificador pasado como parametro"));

			entity.setReferenciaId(areaDeReferencia.getId());
		}

		this.areaRepository.save(entity);
	}

	@Override
	public List<AreaDTO> findAll(int page, int size, String search, String orderBy, String orientation) {

		PageRequest pageRequest = PageRequest.of(page, size);

		Page<Area> areaPage = areaRepository.findAll(new Specification<Area>() {
			@Override
			public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();

				if (StringUtils.isNotBlank(search)) {
					predicates.add(cb.or(cb.like(cb.lower(root.get("descripcion")), "%" + search.toLowerCase() + "%")));
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

		return this.areaMapper.toListDTO(areaPage.getContent());
	}

	@Override
	public AreaDTO update(AreaDTO area, Long areaId) {
		if (areaId == null) {
			throw MunicipalidadMQRuntimeException.badRequestException("El identificador del area es obligatorio");
		}

		var areaExistente = this.areaRepository.findById(areaId).orElseThrow(() -> MunicipalidadMQRuntimeException
				.notFoundException("No se encontró dependencia con el identificador pasado como parametro"));

		areaExistente.setCodigoPresupuestario(
				area.getCodigoPresupuestario() != null && !area.getCodigoPresupuestario().isBlank()
						? area.getCodigoPresupuestario()
						: areaExistente.getCodigoPresupuestario());

		areaExistente.setDescripcion(
				area.getDescripcion() != null && !area.getDescripcion().isBlank() ? area.getDescripcion()
						: areaExistente.getDescripcion());

		areaExistente.setNivel(area.getNivel() != null ? area.getNivel() : areaExistente.getNivel());

		if (area.getReferenciaId() != null) {

			Area areaDeReferencia = this.areaRepository.findById(area.getReferenciaId())
					.orElseThrow(() -> MunicipalidadMQRuntimeException.notFoundException(
							"No se encontró dependencia con el identificador pasado como parametro"));

			areaExistente.setReferenciaId(areaDeReferencia.getId());
		}

		return this.areaMapper.toDTO(this.areaRepository.save(areaExistente));

	}

	@Override
	public void delete(Long areaId) {
		if (areaId == null) {
			throw MunicipalidadMQRuntimeException.badRequestException("El identificador del area es obligatorio");
		}

		var areaExistente = this.areaRepository.findById(areaId).orElseThrow(() -> MunicipalidadMQRuntimeException
				.notFoundException("No se encontró dependencia con el identificador pasado como parametro"));

		this.areaRepository.delete(areaExistente);

	}
}
