package ar.com.mq.expedientes.api.web;

import ar.com.mq.expedientes.api.model.dto.TipoDocumentoDTO;
import ar.com.mq.expedientes.api.service.interfaces.TipoDocumentoService;
import ar.com.mq.expedientes.core.constants.SwaggerTags;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tipos-documentos")
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600)
@Slf4j
@Api(tags= {SwaggerTags.TIPOS_DOCUMENTOS_TAG})
public class TipoDocumentoController {

    private final TipoDocumentoService tipoDocumentoService;

    @Autowired
    public TipoDocumentoController(TipoDocumentoService tipoDocumentoService) {
        this.tipoDocumentoService = tipoDocumentoService;
    }


    @PostMapping
    public ResponseEntity<Object> save(@RequestBody TipoDocumentoDTO tipoDocumento) {
        log.debug("Por guardar tipo de documento {}", tipoDocumento);
        this.tipoDocumentoService.save(tipoDocumento);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Object> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "orderBy", required = false, defaultValue = "") String orderBy,
            @RequestParam(value = "orientation", required = false, defaultValue = "") String orientation,
            @RequestParam(value = "search", required = false, defaultValue = "") String search) {

        return new ResponseEntity<>(this.tipoDocumentoService.findAll(page, size, search, orderBy, orientation), HttpStatus.OK);
    }


}
