package ar.com.mq.expedientes.api.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.mq.expedientes.api.model.dto.LoginDTO;
import ar.com.mq.expedientes.api.service.interfaces.LoginService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/login")
//@CrossOrigin(origins = {"http://localhost:5173", "*", "https://expedientes-mq-front.vercel.app"}, maxAge = 3600)
@CrossOrigin(origins = { "http://vps-4188220-x.dattaweb.com", "*" }, maxAge = 3600)

@Slf4j
public class LoginController {

	private LoginService loginService;

	@Autowired
	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}

	@PostMapping
	public ResponseEntity<Object> authenticate(@RequestBody LoginDTO loginDTO) {
		log.debug("Por autenticar usuario: {}", loginDTO.getUsername());
		Object object = this.loginService.authenticate(loginDTO);
		Map<String, Object> response = new HashMap<>();
		response.put("token", object);
		return ResponseEntity.ok(response);
	}
}
