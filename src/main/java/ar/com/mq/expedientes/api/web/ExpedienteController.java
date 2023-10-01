package ar.com.mq.expedientes.api.web;

import ar.com.mq.expedientes.api.model.dto.ExpedienteDTO;
import ar.com.mq.expedientes.api.model.dto.ExpedientePagoDTO;
import ar.com.mq.expedientes.api.model.dto.WrapperData;
import ar.com.mq.expedientes.api.service.interfaces.ExpedienteService;
import ar.com.mq.expedientes.core.constants.SwaggerTags;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/expedientes")
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600)
@Slf4j
@Api(tags= {SwaggerTags.EXPEDIENTES_TAG})
public class ExpedienteController {

    private final ExpedienteService expedienteService;

    @Autowired
    public ExpedienteController(ExpedienteService expedienteService) {
        this.expedienteService = expedienteService;
    }

    @PostMapping(value = "/pagos/caratular")
    public ResponseEntity<Object> save(@RequestBody ExpedientePagoDTO expediente) {
        log.debug("Por caratular expediente {}", expediente);
        this.expedienteService.save(expediente);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody ExpedienteDTO expedienteDTO){
        log.debug("Por actualizar expediente {}", expedienteDTO);
        this.expedienteService.update(id,expedienteDTO);
        return new ResponseEntity<>("Expediente actualizado exitosamente", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "orderBy", required = false, defaultValue = "") String orderBy,
            @RequestParam(value = "orientation", required = false, defaultValue = "") String orientation,
            @RequestParam(value = "search", required = false, defaultValue = "") String search) {

        WrapperData data = this.expedienteService.findAll(page,size,search,orderBy,orientation);

        return new ResponseEntity<>(data, HttpStatus.OK);
    }


}
