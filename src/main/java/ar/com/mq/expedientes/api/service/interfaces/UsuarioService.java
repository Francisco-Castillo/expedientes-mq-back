package ar.com.mq.expedientes.api.service.interfaces;

import ar.com.mq.expedientes.api.model.dto.UsuarioDTO;

public interface UsuarioService {

    void create(UsuarioDTO usuarioDTO);

    UsuarioDTO getUserInfo(String username);
}
