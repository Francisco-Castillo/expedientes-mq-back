package ar.com.mq.expedientes.api.web;

import ar.com.mq.expedientes.api.model.dto.UsuarioDTO;
import ar.com.mq.expedientes.api.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/authenticate")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody UsuarioDTO usuario) {
        this.usuarioService.create(usuario);
        return new ResponseEntity("OK", HttpStatus.CREATED);
    }

    @PostMapping(value = "/user-info")
    public ResponseEntity<Object> getUserInfo(@RequestBody UsuarioDTO usuario) {
        UsuarioDTO userInfo = this.usuarioService.getUserInfo(usuario.getEmail());
        return new ResponseEntity(userInfo, HttpStatus.OK);
    }
}
