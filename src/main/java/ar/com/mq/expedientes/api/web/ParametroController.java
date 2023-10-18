package ar.com.mq.expedientes.api.web;

import ar.com.mq.expedientes.api.model.dto.ParametroDTO;
import ar.com.mq.expedientes.api.service.interfaces.ParametroService;
import ar.com.mq.expedientes.core.constants.SwaggerTags;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/parametros")
@Api(tags= {SwaggerTags.PARAMETROS_TAG})
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600)
public class ParametroController {

    private final ParametroService parametroService;

    @Autowired
    public ParametroController(ParametroService parametroService) {
        this.parametroService = parametroService;
    }

    @GetMapping
    public ResponseEntity<Object> findByName(@RequestParam String name) {
        ParametroDTO parametro = this.parametroService.findByNombre(name);
        return new ResponseEntity<>(parametro, HttpStatus.OK);
    }
}
