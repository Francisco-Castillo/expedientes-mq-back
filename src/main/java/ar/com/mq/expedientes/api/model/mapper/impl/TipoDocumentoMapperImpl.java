package ar.com.mq.expedientes.api.model.mapper.impl;

import ar.com.mq.expedientes.api.model.dto.TipoDocumentoDTO;
import ar.com.mq.expedientes.api.model.entity.TipoDocumento;
import ar.com.mq.expedientes.api.model.mapper.interfaces.TipoDocumentoMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class TipoDocumentoMapperImpl implements TipoDocumentoMapper {
    @Override
    public TipoDocumento toEntity(TipoDocumentoDTO dto) {
        if (ObjectUtils.isEmpty(dto)) return null;
        return TipoDocumento.builder()
                .id(dto.getId())
                .descripcion(dto.getDescripcion())
                .build();
    }

    @Override
    public TipoDocumentoDTO toDTO(TipoDocumento entity) {
        if (ObjectUtils.isEmpty(entity)) return null;
        return TipoDocumentoDTO.builder()
                .id(entity.getId())
                .descripcion(entity.getDescripcion())
                .build();
    }

    @Override
    public List<TipoDocumentoDTO> toListDTO(List<TipoDocumento> entities) {
        if (CollectionUtils.isEmpty(entities)) return Collections.emptyList();
        List<TipoDocumentoDTO> tipos = new ArrayList<>();
        entities.forEach(entity -> tipos.add(toDTO(entity)));
        return tipos;
    }
}
