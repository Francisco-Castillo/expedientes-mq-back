package ar.com.mq.expedientes.api.web;

import ar.com.mq.expedientes.api.model.dto.DocumentoDTO;
import ar.com.mq.expedientes.api.service.interfaces.DocumentoService;
import ar.com.mq.expedientes.core.constants.SwaggerTags;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600)
@RequestMapping(value = "/documentos")
@RestController
@Slf4j
@Api(tags= {SwaggerTags.DOCUMENTOS_TAG})

public class DocumentoController {

    private final DocumentoService documentoService;

    @Autowired
    public DocumentoController(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    @PostMapping
    public ResponseEntity<Object> save(HttpServletRequest request , @RequestParam String data){
        log.debug("Por guardar documento {}", data);
        this.documentoService.save(request, data);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
