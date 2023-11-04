package ar.com.mq.expedientes.api.service.impl;

import ar.com.mq.expedientes.api.model.dto.LoginDTO;
import ar.com.mq.expedientes.api.model.entity.Usuario;
import ar.com.mq.expedientes.api.service.interfaces.LoginService;
import ar.com.mq.expedientes.api.service.interfaces.UsuarioService;
import ar.com.mq.expedientes.api.service.repository.UsuarioRepository;
import ar.com.mq.expedientes.core.exception.exceptions.MunicipalidadMQRuntimeException;
import ar.com.mq.expedientes.core.utils.PasswordUtils;
import ar.com.mq.expedientes.core.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    private UsuarioService usuarioService;

    @Autowired
    public LoginServiceImpl(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public Object authenticate(LoginDTO login) {
        try {

            Usuario usuario = this.usuarioService.findByEmail(login.getUsername());

            if (!PasswordUtils.encriptar(login.getPassword()).equals(usuario.getPassword())){
                throw MunicipalidadMQRuntimeException.conflictException("Usuario o contraseña incorrectos");
            }

            if (usuario.getPrimerLogin()==0){
                throw MunicipalidadMQRuntimeException.conflictException("Debe cambiar su contraseña.");
            }

            return  "Bearer "+ TokenUtils.create(usuario.getId(), usuario.getEmail());

        } catch (Exception e) {
            log.error("Ocurrio un error al intentar autenticar usuario. Excepcion: {}", e.getMessage());
            throw e;
        }

    }
}
