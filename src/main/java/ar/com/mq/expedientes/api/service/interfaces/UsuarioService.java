package ar.com.mq.expedientes.api.service.interfaces;

import ar.com.mq.expedientes.api.model.dto.UsuarioBaseDTO;
import ar.com.mq.expedientes.api.model.dto.UsuarioDTO;
import ar.com.mq.expedientes.api.model.dto.WrapperData;
import ar.com.mq.expedientes.api.model.entity.Usuario;

public interface UsuarioService {

    void create(UsuarioDTO usuarioDTO);

    UsuarioBaseDTO getUserInfo(String username);

    WrapperData findAll(int page, int size, String search, String orderBy, String orientation);

    Usuario findByEmail(String email);

    void changePassword(String email, String password);

    void changeStatus (Long userId, Integer status);
    
    UsuarioBaseDTO update(Long id, UsuarioBaseDTO user);
}
