package ar.com.mq.expedientes.api.model.mapper.impl;

import ar.com.mq.expedientes.api.model.dto.ExpedienteDTO;
import ar.com.mq.expedientes.api.model.entity.Expediente;
import ar.com.mq.expedientes.api.model.mapper.interfaces.ExpedienteMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ExpedienteMapperImpl implements ExpedienteMapper {

    @Override
    public Expediente toEntity(ExpedienteDTO dto) {

        if (ObjectUtils.isEmpty(dto)) return null;

        return Expediente.builder()
                .id(dto.getId())
                .numero(dto.getNumero())
                .referencia(dto.getReferencia())
                .fechaCaratulacion(dto.getFechaCaratulacion())
                .descripcion(dto.getDescripcion())
                .codigoTramite(dto.getCodigoTramite())
                .tipo(dto.getTipo())
                .estado(dto.getEstado())
                .build();
    }

    @Override
    public ExpedienteDTO toDTO(Expediente entity) {
        if (ObjectUtils.isEmpty(entity)) return null;

        return ExpedienteDTO.builder()
                .id(entity.getId())
                .numero(entity.getNumero())
                .referencia(entity.getReferencia())
                .fechaCaratulacion(entity.getFechaCaratulacion())
                .descripcion(entity.getDescripcion())
                .codigoTramite(entity.getCodigoTramite())
                .tipo(entity.getTipo())
                .estado(entity.getEstado())
                .build();
    }

    @Override
    public List<ExpedienteDTO> toListDTO(List<Expediente> entities) {
        if (CollectionUtils.isEmpty(entities)) return Collections.emptyList();
        List<ExpedienteDTO> expedientes = new ArrayList<>();
        entities.forEach(entity -> expedientes.add(toDTO(entity)));
        return expedientes;
    }


}
