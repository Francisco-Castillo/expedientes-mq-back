package ar.com.mq.expedientes.api.model.mapper.impl;

import ar.com.mq.expedientes.api.model.dto.DocumentoDTO;
import ar.com.mq.expedientes.api.model.dto.ExpedienteDTO;
import ar.com.mq.expedientes.api.model.entity.Documento;
import ar.com.mq.expedientes.api.model.entity.Expediente;
import ar.com.mq.expedientes.api.model.entity.TipoDocumento;
import ar.com.mq.expedientes.api.model.mapper.interfaces.DocumentoMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class DocumentoMapperImpl implements DocumentoMapper {
    @Override
    public Documento toEntity(DocumentoDTO dto) {

        if (ObjectUtils.isEmpty(dto)) return null;

        return Documento.builder()
                .id(dto.getId())
                .fechaSubida(dto.getFechaSubida())
                .observaciones(dto.getObservaciones())
                .nombre(dto.getNombre())
                .ubicacion(dto.getUbicacion())
                .expediente(Expediente.builder().id(dto.getExpedienteId()).build())
                .tipoDocumento(TipoDocumento.builder().id(dto.getTipoDocumentoId()).build())
                .tipoArchivo(dto.getTipoArchivo())
                .build();
    }

    @Override
    public DocumentoDTO toDTO(Documento entity) {
        if (ObjectUtils.isEmpty(entity)) return null;
        return DocumentoDTO.builder()
                .id(entity.getId())
                .ubicacion(entity.getUbicacion())
                .nombre(entity.getNombre())
                .expedienteId(entity.getExpediente().getId())
                .tipoDocumentoId(entity.getTipoDocumento().getId())
                .tipoArchivo(entity.getTipoArchivo())
                .fechaSubida(entity.getFechaSubida())
                .build();
    }

    @Override
    public List<DocumentoDTO> toListDTO(List<Documento> entities) {
        if (CollectionUtils.isEmpty(entities)) return Collections.emptyList();
        List<DocumentoDTO> expedientes = new ArrayList<>();
        entities.forEach(entity -> expedientes.add(toDTO(entity)));
        return expedientes;
    }
}
