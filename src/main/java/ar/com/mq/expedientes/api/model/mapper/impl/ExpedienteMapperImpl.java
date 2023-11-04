package ar.com.mq.expedientes.api.model.mapper.impl;

import ar.com.mq.expedientes.api.model.dto.ExpedienteDTO;
import ar.com.mq.expedientes.api.model.entity.Expediente;
import ar.com.mq.expedientes.api.model.mapper.interfaces.DocumentoMapper;
import ar.com.mq.expedientes.api.model.mapper.interfaces.ExpedienteMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ExpedienteMapperImpl implements ExpedienteMapper {

    private final DocumentoMapper documentoMapper;

    @Autowired
    public ExpedienteMapperImpl(DocumentoMapper documentoMapper) {
        this.documentoMapper = documentoMapper;
    }

    @Override
    public Expediente toEntity(ExpedienteDTO dto) {

        if (ObjectUtils.isEmpty(dto)) return null;

        return Expediente.builder()
                .id(dto.getId())
                .numero(dto.getNumero())
                .cantidadFojas(dto.getCantidadFojas())
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
                .cantidadFojas(entity.getCantidadFojas())
                .referencia(entity.getReferencia())
                .fechaCaratulacion(entity.getFechaCaratulacion())
                .descripcion(entity.getDescripcion())
                .codigoTramite(entity.getCodigoTramite())
                .tipo(entity.getTipo())
                .estado(entity.getEstado())
                .documentos(this.documentoMapper.toListDTO(entity.getDocumentos()))
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
