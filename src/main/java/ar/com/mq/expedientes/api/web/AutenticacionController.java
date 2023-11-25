package ar.com.mq.expedientes.api.web;

import ar.com.mq.expedientes.api.model.dto.UsuarioDTO;
import ar.com.mq.expedientes.api.service.interfaces.UsuarioService;
import ar.com.mq.expedientes.core.constants.SwaggerTags;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/authenticate")
@Api(tags= {SwaggerTags.AUTENTICACION_TAG})
@CrossOrigin(origins = {"http://localhost:5173", "*"}, maxAge = 3600)
public class AutenticacionController {

    private final UsuarioService usuarioService;

    @Autowired
    public AutenticacionController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody UsuarioDTO usuario) {
        this.usuarioService.create(usuario);
        return new ResponseEntity("OK", HttpStatus.CREATED);
    }
}
