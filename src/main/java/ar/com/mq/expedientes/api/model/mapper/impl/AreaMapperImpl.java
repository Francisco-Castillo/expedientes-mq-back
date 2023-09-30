package ar.com.mq.expedientes.api.model.mapper.impl;

import ar.com.mq.expedientes.api.model.dto.AreaDTO;
import ar.com.mq.expedientes.api.model.entity.Area;
import ar.com.mq.expedientes.api.model.mapper.interfaces.AreaMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class AreaMapperImpl implements AreaMapper {
    @Override
    public Area toEntity(AreaDTO dto) {
        if (ObjectUtils.isEmpty(dto)) return null;

        return Area.builder()
                .id(dto.getId())
                .descripcion(dto.getDescripcion())
                .referenciaId(dto.getReferenciaId())
                .nivel(dto.getNivel())
                .codigoPresupuestario(dto.getCodigoPresupuestario())
                .build();
    }

    @Override
    public AreaDTO toDTO(Area entity) {
        if (ObjectUtils.isEmpty(entity)) return null;

        return AreaDTO.builder()
                .id(entity.getId())
                .descripcion(entity.getDescripcion())
                .referenciaId(entity.getReferenciaId())
                .nivel(entity.getNivel())
                .codigoPresupuestario(entity.getCodigoPresupuestario())
                .build();
    }

    @Override
    public List<AreaDTO> toListDTO(List<Area> entities) {
        if (CollectionUtils.isEmpty(entities)) return Collections.emptyList();
        List<AreaDTO> areas = new ArrayList<>();
        entities.forEach(entity -> areas.add(toDTO(entity)));
        return areas;
    }
}
