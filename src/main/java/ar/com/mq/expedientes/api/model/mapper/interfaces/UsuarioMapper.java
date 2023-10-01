package ar.com.mq.expedientes.api.model.mapper.interfaces;

import ar.com.mq.expedientes.api.model.dto.UsuarioBaseDTO;
import ar.com.mq.expedientes.api.model.dto.UsuarioDTO;
import ar.com.mq.expedientes.api.model.entity.Usuario;
import ar.com.mq.expedientes.core.business.MunicipalidadMQMapper;

import java.util.List;

public interface UsuarioMapper extends MunicipalidadMQMapper<Usuario, UsuarioDTO> {

    List<UsuarioBaseDTO> toListBaseDTO(List<Usuario> entities);
}
