package ar.com.mq.expedientes.api.model.mapper.impl;

import ar.com.mq.expedientes.api.model.dto.UsuarioBaseDTO;
import ar.com.mq.expedientes.api.model.dto.UsuarioDTO;
import ar.com.mq.expedientes.api.model.entity.Usuario;
import ar.com.mq.expedientes.api.model.mapper.interfaces.AreaMapper;
import ar.com.mq.expedientes.api.model.mapper.interfaces.UsuarioMapper;
import ar.com.mq.expedientes.core.utils.PasswordUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class UsuarioMapperImpl implements UsuarioMapper {

    private final AreaMapper areaMapper;

    @Autowired
    public UsuarioMapperImpl(AreaMapper areaMapper) {
        this.areaMapper = areaMapper;
    }

    @Override
    public Usuario toEntity(UsuarioDTO dto) {

        if (ObjectUtils.isEmpty(dto)) {
            return null;
        }

        return Usuario.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .dni(dto.getDocumento())
                .estado(dto.getEstado())
                .primerLogin(dto.getPrimerLogin())
                .email(dto.getEmail())
                .fechaAlta(dto.getFechaAlta())
                .password(PasswordUtils.encriptar(dto.getPassword()))
                .uuid(dto.getUuid())
                .areaId(this.areaMapper.toEntity(dto.getArea()))
                .build();
    }

    @Override
    public UsuarioDTO toDTO(Usuario entity) {
        if (ObjectUtils.isEmpty(entity)) {
            return null;
        }

        return UsuarioDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .apellido(entity.getApellido())
                .documento(entity.getDni())
                .estado(entity.getEstado())
                .primerLogin(entity.getPrimerLogin())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .fechaAlta(entity.getFechaAlta())
                .uuid(entity.getUuid())
                .area(this.areaMapper.toDTO(entity.getAreaId()))
                .build();
    }

    @Override
    public UsuarioBaseDTO toBaseDTO(Usuario entity) {
        if (ObjectUtils.isEmpty(entity)) {
            return null;
        }

        return UsuarioBaseDTO.builder()
                .id(entity.getId())
        		.email(entity.getEmail())
                .nombre(entity.getNombre())
                .apellido(entity.getApellido())
                .documento(entity.getDni())
                .estado(entity.getEstado())
                .primerLogin(entity.getPrimerLogin())
                .area(this.areaMapper.toDTO(entity.getAreaId()))
                .build();
    }

    @Override
    public List<UsuarioDTO> toListDTO(List<Usuario> entities) {
        if (CollectionUtils.isEmpty(entities)) return Collections.emptyList();
        List<UsuarioDTO> usuarios = new ArrayList<>();
        entities.forEach(entity -> usuarios.add(toDTO(entity)));
        return usuarios;
    }

    @Override
    public List<UsuarioBaseDTO> toListBaseDTO(List<Usuario> entities) {
        if (CollectionUtils.isEmpty(entities)) return Collections.emptyList();
        List<UsuarioBaseDTO> usuarios = new ArrayList<>();
        entities.forEach(entity -> usuarios.add(toBaseDTO(entity)));
        return usuarios;
    }

}
