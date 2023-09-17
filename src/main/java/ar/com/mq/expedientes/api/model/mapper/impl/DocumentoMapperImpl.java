package ar.com.mq.expedientes.api.model.mapper.impl;

import ar.com.mq.expedientes.api.model.dto.DocumentoDTO;
import ar.com.mq.expedientes.api.model.entity.Documento;
import ar.com.mq.expedientes.api.model.entity.Expediente;
import ar.com.mq.expedientes.api.model.entity.TipoDocumento;
import ar.com.mq.expedientes.api.model.mapper.interfaces.DocumentoMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

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
                .ubicacion(dto.getUbicacion())
                .expediente(Expediente.builder().id(dto.getExpedienteId()).build())
                .tipoDocumentoId(TipoDocumento.builder().id(dto.getTipoDocumentoId()).build())
                .build();
    }

    @Override
    public DocumentoDTO toDTO(Documento entity) {
        if (ObjectUtils.isEmpty(entity)) return null;
        return DocumentoDTO.builder()
                .id(entity.getId())
                .ubicacion(entity.getUbicacion())
                .expedienteId(entity.getExpediente().getId())
                .tipoDocumentoId(entity.getTipoDocumentoId().getId())
                .fechaSubida(entity.getFechaSubida())
                .build();
    }

    @Override
    public List<DocumentoDTO> toListDTO(List<Documento> entities) {
        return null;
    }
}
