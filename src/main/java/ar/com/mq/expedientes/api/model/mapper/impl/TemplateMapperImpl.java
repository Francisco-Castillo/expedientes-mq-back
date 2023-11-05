package ar.com.mq.expedientes.api.model.mapper.impl;

import ar.com.mq.expedientes.api.model.dto.TemplateDTO;
import ar.com.mq.expedientes.api.model.entity.Template;
import ar.com.mq.expedientes.api.model.mapper.interfaces.TemplateMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TemplateMapperImpl implements TemplateMapper {
    @Override
    public Template toEntity(TemplateDTO dto) {
        return null;
    }

    @Override
    public TemplateDTO toDTO(Template entity) {

        if (ObjectUtils.isEmpty(entity)) return null;

        return TemplateDTO.builder()
                .id(entity.getId())
                .content(entity.getContenido())
                .type(entity.getTipo())
                .build();
    }

    @Override
    public List<TemplateDTO> toListDTO(List<Template> entities) {
        return null;
    }
}
