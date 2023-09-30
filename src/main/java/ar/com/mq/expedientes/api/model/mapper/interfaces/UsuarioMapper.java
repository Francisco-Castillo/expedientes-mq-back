package ar.com.mq.expedientes.api.model.mapper.interfaces;

import ar.com.mq.expedientes.api.model.dto.UsuarioDTO;
import ar.com.mq.expedientes.api.model.entity.Usuario;
import ar.com.mq.expedientes.core.business.MunicipalidadMQMapper;

public interface UsuarioMapper extends MunicipalidadMQMapper<Usuario, UsuarioDTO> {
}
