package ar.com.mq.expedientes.api.model.mapper.impl;

import ar.com.mq.expedientes.api.model.dto.ParametroDTO;
import ar.com.mq.expedientes.api.model.entity.Parametro;
import ar.com.mq.expedientes.api.model.mapper.interfaces.ParametroMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ParametroMapperImpl implements ParametroMapper {
    @Override
    public Parametro toEntity(ParametroDTO dto) {

        if (ObjectUtils.isEmpty(dto)) {
            return null;
        }

        return Parametro.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .valor(dto.getValor())
                .build();
    }

    @Override
    public ParametroDTO toDTO(Parametro entity) {
        if (ObjectUtils.isEmpty(entity)) {
            return null;
        }

        return ParametroDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .valor(entity.getValor())
                .build();
    }

    @Override
    public List<ParametroDTO> toListDTO(List<Parametro> entities) {
        return null;
    }
}
