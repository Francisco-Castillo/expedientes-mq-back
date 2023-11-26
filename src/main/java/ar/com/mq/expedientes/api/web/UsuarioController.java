package ar.com.mq.expedientes.api.web;

import org.hibernate.boot.model.relational.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.mq.expedientes.api.model.dto.UsuarioBaseDTO;
import ar.com.mq.expedientes.api.model.dto.UsuarioDTO;
import ar.com.mq.expedientes.api.model.dto.WrapperData;
import ar.com.mq.expedientes.api.service.interfaces.UsuarioService;
import ar.com.mq.expedientes.core.constants.SwaggerTags;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/usuarios")
@Api(tags = { SwaggerTags.USUARIOS_TAG })
@CrossOrigin(origins = "https://expedientes-mq-front-m0qsu3smm-francisco-castillos-projects.vercel.app", maxAge = 3600)
@Slf4j
public class UsuarioController {

	private final UsuarioService usuarioService;

	@Autowired
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@GetMapping(value = "/user-info")
	public ResponseEntity<Object> getUserInfo(
			@RequestParam(value = "email", required = false, defaultValue = "") String email) {
		log.debug("User info: {}", email);
		UsuarioBaseDTO user = this.usuarioService.getUserInfo(email);
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
			@RequestParam(value = "nombre", required = false, defaultValue = "") String nombre,
			@RequestParam(value = "apellido", required = false, defaultValue = "") String apellido,
			@RequestParam(value = "dni", required = false, defaultValue = "") String dni,
			@RequestParam(value = "email", required = false, defaultValue = "") String email,
			@RequestParam(value = "orderBy", required = false, defaultValue = "") String orderBy,
			@RequestParam(value = "orientation", required = false, defaultValue = "") String orientation,
			@RequestParam(value = "universalFilter", required = false, defaultValue = "") String universalFilter) {
		WrapperData data = this.usuarioService.findAll(page, size, apellido, nombre, dni, email, universalFilter, orderBy, orientation);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody UsuarioBaseDTO user) {
		UsuarioBaseDTO baseUser = this.usuarioService.update(id, user);
		return new ResponseEntity<>(baseUser, HttpStatus.OK);
	}

	@PutMapping(value = "/cambiar-password")
	public ResponseEntity<Object> changePassword(@RequestBody UsuarioDTO user) {
		this.usuarioService.changePassword(user.getEmail(), user.getPassword());
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}

	@PutMapping(value = "/cambiar-estado")
	public ResponseEntity<Object> changeStatus(@RequestBody UsuarioDTO user) {
		this.usuarioService.changeStatus(user.getId(), user.getEstado());
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}
}
