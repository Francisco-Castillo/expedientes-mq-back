package ar.com.mq.expedientes.api.service.interfaces;

import ar.com.mq.expedientes.api.model.dto.UsuarioDTO;
import ar.com.mq.expedientes.api.model.dto.WrapperData;

public interface UsuarioService {

    void create(UsuarioDTO usuarioDTO);

    UsuarioDTO getUserInfo(String username);

    WrapperData findAll(int page, int size, String search, String orderBy, String orientation);
}
