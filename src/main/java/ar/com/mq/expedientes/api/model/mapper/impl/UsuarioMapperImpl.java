package ar.com.mq.expedientes.api.model.mapper.impl;

import ar.com.mq.expedientes.api.model.dto.UsuarioDTO;
import ar.com.mq.expedientes.api.model.entity.Usuario;
import ar.com.mq.expedientes.api.model.mapper.interfaces.UsuarioMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioMapperImpl implements UsuarioMapper {


    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioMapperImpl() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Usuario toEntity(UsuarioDTO dto) {

        if (ObjectUtils.isEmpty(dto)) {
            return null;
        }

        return Usuario.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .fechaAlta(dto.getFechaAlta())
                .uuid(dto.getUuid())
                .build();
    }

    @Override
    public UsuarioDTO toDTO(Usuario entity) {
        if (ObjectUtils.isEmpty(entity)) {
            return null;
        }

        return UsuarioDTO.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .fechaAlta(entity.getFechaAlta())
                .uuid(entity.getUuid())
                .build();
    }

    @Override
    public List<UsuarioDTO> toListDTO(List<Usuario> entities) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
