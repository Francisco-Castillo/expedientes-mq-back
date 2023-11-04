package ar.com.mq.expedientes.api.web;

import ar.com.mq.expedientes.api.model.dto.UsuarioBaseDTO;
import ar.com.mq.expedientes.api.model.dto.UsuarioDTO;
import ar.com.mq.expedientes.api.model.dto.WrapperData;
import ar.com.mq.expedientes.api.service.interfaces.UsuarioService;
import ar.com.mq.expedientes.core.constants.SwaggerTags;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/usuarios")
@Api(tags = {SwaggerTags.USUARIOS_TAG})
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600)
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping(value = "/user-info")
    public ResponseEntity<Object> getUserInfo(@RequestBody UsuarioDTO usuario) {
        UsuarioBaseDTO user = this.usuarioService.getUserInfo(usuario.getEmail());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody UsuarioDTO usuario) {
        this.usuarioService.create(usuario);
        return new ResponseEntity<>("OK", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Object> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                          @RequestParam(value = "size", defaultValue = "10") int size,
                                          @RequestParam(value = "orderBy", required = false, defaultValue = "") String orderBy,
                                          @RequestParam(value = "orientation", required = false, defaultValue = "") String orientation,
                                          @RequestParam(value = "search", required = false, defaultValue = "") String search) {
        WrapperData data = this.usuarioService.findAll(page, size, search, orderBy, orientation);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PutMapping(value = "/cambiar-password")
    public ResponseEntity<Object> changePassword(@RequestBody UsuarioDTO user) {
        this.usuarioService.changePassword(user.getId(), user.getPassword());
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PutMapping(value = "/cambiar-estado")
    public ResponseEntity<Object> changeStatus(@RequestBody UsuarioDTO user) {
        this.usuarioService.changeStatus(user.getId(), user.getEstado());
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
