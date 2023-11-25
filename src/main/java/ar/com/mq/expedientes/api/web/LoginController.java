package ar.com.mq.expedientes.api.web;

import ar.com.mq.expedientes.api.model.dto.LoginDTO;
import ar.com.mq.expedientes.api.service.interfaces.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/login")
@CrossOrigin(origins = {"http://localhost:5173", "*", "https://expedientes-mq-front-n2aabfrry-francisco-castillos-projects.vercel.app"}, maxAge = 3600)
@Slf4j
public class LoginController {

    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<Object> authenticate(@RequestBody LoginDTO loginDTO){
        log.debug("Por autenticar usuario: {}", loginDTO.getUsername());
        Object object = this.loginService.authenticate(loginDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("token", object);
        return ResponseEntity.ok(response);
    }
}
