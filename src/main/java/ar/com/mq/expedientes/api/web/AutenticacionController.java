package ar.com.mq.expedientes.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.mq.expedientes.api.model.dto.UsuarioDTO;
import ar.com.mq.expedientes.api.service.interfaces.UsuarioService;
import ar.com.mq.expedientes.core.constants.SwaggerTags;
import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/authenticate")
@Api(tags = { SwaggerTags.AUTENTICACION_TAG })
//@CrossOrigin(origins = {"http://localhost:5173", "*"}, maxAge = 3600)
@CrossOrigin(origins = { "http://vps-4188220-x.dattaweb.com", "*" }, maxAge = 3600)
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
