package ar.com.mq.expedientes.api.model.mapper.impl;

import ar.com.mq.expedientes.api.model.dto.ExpedientePagoDTO;
import ar.com.mq.expedientes.api.model.entity.ExpedientePago;
import ar.com.mq.expedientes.api.model.mapper.interfaces.ExpedientePagoMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExpedientePagoMapperImpl implements ExpedientePagoMapper {

    @Override
    public ExpedientePago toEntity(ExpedientePagoDTO dto) {

        if (ObjectUtils.isEmpty(dto)) return null;

        return ExpedientePago.expedientePagoBuilder()
                .id(dto.getId())
                .numero(dto.getNumero())
                .referencia(dto.getReferencia())
                .fechaCaratulacion(dto.getFechaCaratulacion())
                .descripcion(dto.getDescripcion())
                .codigoTramite(dto.getCodigoTramite())
                .build();
    }

    @Override
    public ExpedientePagoDTO toDTO(ExpedientePago entity) {
        if (ObjectUtils.isEmpty(entity)) return null;

        return ExpedientePagoDTO.expedientePagoDTOBuilder()
                .id(entity.getId())
                .numero(entity.getNumero())
                .referencia(entity.getReferencia())
                .fechaCaratulacion(entity.getFechaCaratulacion())
                .descripcion(entity.getDescripcion())
                .codigoTramite(entity.getCodigoTramite())
                .build();
    }

    @Override
    public List<ExpedientePagoDTO> toListDTO(List<ExpedientePago> entities) {
        return null;
    }
}
