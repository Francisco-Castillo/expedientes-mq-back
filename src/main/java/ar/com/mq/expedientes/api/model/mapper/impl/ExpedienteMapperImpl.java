package ar.com.mq.expedientes.api.model.mapper.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.com.mq.expedientes.api.model.dto.ExpedienteDTO;
import ar.com.mq.expedientes.api.model.dto.UsuarioDTO;
import ar.com.mq.expedientes.api.model.entity.Expediente;
import ar.com.mq.expedientes.api.model.entity.Usuario;
import ar.com.mq.expedientes.api.model.mapper.interfaces.DocumentoMapper;
import ar.com.mq.expedientes.api.model.mapper.interfaces.ExpedienteMapper;
import ar.com.mq.expedientes.api.model.mapper.interfaces.UsuarioMapper;

@Component
public class ExpedienteMapperImpl implements ExpedienteMapper {

	private final DocumentoMapper documentoMapper;
	private final UsuarioMapper usuarioMapper;

	@Autowired
	public ExpedienteMapperImpl(DocumentoMapper documentoMapper, UsuarioMapper usuarioMapper) {
		this.documentoMapper = documentoMapper;
		this.usuarioMapper = usuarioMapper;
	}

	@Override
	public Expediente toEntity(ExpedienteDTO dto) {

		if (ObjectUtils.isEmpty(dto)) {
			return null;
		}

		return Expediente.builder().id(dto.getId()).iniciador(dto.getIniciador()).numero(dto.getNumero())
				.cantidadFojas(dto.getCantidadFojas()).referencia(dto.getReferencia())
				.fechaCaratulacion(dto.getFechaCaratulacion()).descripcion(dto.getDescripcion())
				.codigoTramite(dto.getCodigoTramite()).tipo(dto.getTipo()).monto(dto.getMonto()).estado(dto.getEstado())
				.responsable(dto.getResponsable()).usuario(Usuario.builder().id(dto.getUsuario().getId()).build())
				.build();
	}

	@Override
	public ExpedienteDTO toDTO(Expediente entity) {
		if (ObjectUtils.isEmpty(entity)) {
			return null;
		}
		return ExpedienteDTO.builder().id(entity.getId()).iniciador(entity.getIniciador()).numero(entity.getNumero())
				.cantidadFojas(entity.getCantidadFojas()).referencia(entity.getReferencia())
				.fechaCaratulacion(entity.getFechaCaratulacion()).descripcion(entity.getDescripcion())
				.codigoTramite(entity.getCodigoTramite()).tipo(entity.getTipo()).estado(entity.getEstado())
				.documentos(this.documentoMapper.toListDTO(entity.getDocumentos()))
				.usuario(UsuarioDTO.builder().id(entity.getUsuario().getId())
						.apellido(entity.getUsuario().getApellido()).nombre(entity.getUsuario().getNombre()).build())
				.responsable(entity.getResponsable()).monto(entity.getMonto()).build();

		// TODO: Quitar campos extras.
	}

	@Override
	public List<ExpedienteDTO> toListDTO(List<Expediente> entities) {
		if (CollectionUtils.isEmpty(entities)) {
			return Collections.emptyList();
		}
		List<ExpedienteDTO> expedientes = new ArrayList<>();
		entities.forEach(entity -> expedientes.add(toDTO(entity)));
		return expedientes;
	}

}
