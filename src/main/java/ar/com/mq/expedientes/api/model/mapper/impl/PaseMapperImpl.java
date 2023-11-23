package ar.com.mq.expedientes.api.model.mapper.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import ar.com.mq.expedientes.api.model.dto.PaseDTO;
import ar.com.mq.expedientes.api.model.entity.Expediente;
import ar.com.mq.expedientes.api.model.entity.Pase;
import ar.com.mq.expedientes.api.model.entity.Usuario;
import ar.com.mq.expedientes.api.model.mapper.interfaces.PaseMapper;

@Component
public class PaseMapperImpl implements PaseMapper{

	@Override
	public Pase toEntity(PaseDTO dto) {
		if (ObjectUtils.isEmpty(dto)) {
			return null;
		}
		
		return Pase.builder()
				.id(dto.getId())
				.expediente(Expediente.builder().id(dto.getExpedienteId()).build())
				.fechaHora(dto.getFechaHora())
				.usuarioEmisor(Usuario.builder().id(dto.getUsuarioEmisorId()).build())
				.usuarioReceptor(Usuario.builder().id(dto.getUsuarioReceptorId()).build())
				.observaciones(dto.getObservaciones())
				.build();
	}

	@Override
	public PaseDTO toDTO(Pase entity) {
		if (ObjectUtils.isEmpty(entity)) {
			return null;
		}
		
		return PaseDTO.builder()
				.id(entity.getId())
				.fechaHora(entity.getFechaHora())
				.observaciones(entity.getObservaciones())
				.expedienteId(entity.getExpediente().getId())
				.usuarioEmisorId(entity.getUsuarioEmisor().getId())
				.usuarioReceptorId(entity.getUsuarioReceptor().getId())
				.build();
	}

	@Override
	public List<PaseDTO> toListDTO(List<Pase> entities) {
		return entities.stream().map(this::toDTO).collect(Collectors.toList());
	}

}
