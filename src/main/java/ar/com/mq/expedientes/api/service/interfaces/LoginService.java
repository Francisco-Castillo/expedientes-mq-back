package ar.com.mq.expedientes.api.service.interfaces;

import ar.com.mq.expedientes.api.model.dto.LoginDTO;

public interface LoginService {

    Object authenticate(LoginDTO login);
}
