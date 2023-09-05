package ar.com.mq.expedientes.api.web;

import ar.com.mq.expedientes.api.model.dto.ExpedientePagoDTO;
import ar.com.mq.expedientes.api.model.dto.TipoDocumentoDTO;
import ar.com.mq.expedientes.api.model.dto.WrapperData;
import ar.com.mq.expedientes.api.service.interfaces.ExpedienteService;
import ar.com.mq.expedientes.api.service.interfaces.TipoDocumentoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tipos-documentos")
@CrossOrigin("*")
@Slf4j
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

        WrapperData data = this.tipoDocumentoService.findAll(page, size, search, orderBy, orientation);

        return new ResponseEntity<>(data, HttpStatus.OK);
    }


}
