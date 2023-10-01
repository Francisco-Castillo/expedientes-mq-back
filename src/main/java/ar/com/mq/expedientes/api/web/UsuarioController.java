package ar.com.mq.expedientes.api.web;

import ar.com.mq.expedientes.api.model.dto.UsuarioDTO;
import ar.com.mq.expedientes.api.model.dto.WrapperData;
import ar.com.mq.expedientes.api.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping(value = "/user-info")
    public ResponseEntity<Object> getUserInfo(@RequestBody UsuarioDTO usuario) {
        UsuarioDTO userInfo = this.usuarioService.getUserInfo(usuario.getEmail());
        return new ResponseEntity(userInfo, HttpStatus.OK);
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
}
