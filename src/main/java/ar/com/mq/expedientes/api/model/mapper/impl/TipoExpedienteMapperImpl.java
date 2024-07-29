package ar.com.mq.expedientes.api.model.mapper.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import ar.com.mq.expedientes.api.model.dto.TipoExpedienteDTO;
import ar.com.mq.expedientes.api.model.entity.TipoExpediente;
import ar.com.mq.expedientes.api.model.mapper.interfaces.TipoExpedienteMapper;

@Component
public class TipoExpedienteMapperImpl implements TipoExpedienteMapper {

	@Override
	public TipoExpediente toEntity(TipoExpedienteDTO dto) {
		if (ObjectUtils.isEmpty(dto)) {
			return null;
		}
		return TipoExpediente.builder().id(dto.getId()).descripcion(dto.getDescripcion()).build();
	}

	@Override
	public TipoExpedienteDTO toDTO(TipoExpediente entity) {
		if (ObjectUtils.isEmpty(entity)) {
			return null;
		}
		return TipoExpedienteDTO.builder().id(entity.getId()).descripcion(entity.getDescripcion()).build();
	}

	@Override
	public List<TipoExpedienteDTO> toListDTO(List<TipoExpediente> entities) {
		if (CollectionUtils.isEmpty(entities)) {
			return Collections.emptyList();
		}
		List<TipoExpedienteDTO> tipos = new ArrayList<>();
		entities.forEach(entity -> tipos.add(toDTO(entity)));
		return tipos;
	}

}
