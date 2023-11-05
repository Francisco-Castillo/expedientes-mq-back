package ar.com.mq.expedientes.api.web;

import ar.com.mq.expedientes.api.enums.EstadoExpedienteEnum;
import ar.com.mq.expedientes.api.enums.TipoExpedienteEnum;
import ar.com.mq.expedientes.api.model.dto.ExpedienteDTO;
import ar.com.mq.expedientes.api.model.dto.StatusDTO;
import ar.com.mq.expedientes.api.model.dto.WrapperData;
import ar.com.mq.expedientes.api.service.interfaces.ExpedienteService;
import ar.com.mq.expedientes.api.service.interfaces.TemplateService;
import ar.com.mq.expedientes.core.constants.SwaggerTags;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

@RestController
@RequestMapping(value = "/expedientes")
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600)
@Slf4j
@Api(tags = {SwaggerTags.EXPEDIENTES_TAG})
public class ExpedienteController {

    private final ExpedienteService expedienteService;
    private final TemplateService templateService;

    @Autowired
    public ExpedienteController(ExpedienteService expedienteService, TemplateService templateService) {
        this.expedienteService = expedienteService;
        this.templateService = templateService;
    }

    @PostMapping(value = "/caratular")
    public ResponseEntity<Object> save(@RequestBody ExpedienteDTO expediente) {
        log.debug("Por caratular expediente {}", expediente);
        this.expedienteService.save(expediente);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody ExpedienteDTO expedienteDTO) {
        log.debug("Por actualizar expediente {}", expedienteDTO);
        this.expedienteService.update(id, expedienteDTO);
        return new ResponseEntity<>("Expediente actualizado exitosamente", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> findAll(
            @RequestParam(value = "page", required = false,defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "startDate",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "identificator",required = false) String identificator,
            @RequestParam(value = "number",required = false) String number,
            @RequestParam(value = "reference",required = false) String reference,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "status",required = false) String status,
            @RequestParam(value = "universalFilter", required = false, defaultValue = "") String universalFilter,
            @RequestParam(value = "includeDocuments", required = false, defaultValue = "false") boolean includeDocuments,
            @RequestParam(value = "orderBy", required = false, defaultValue = "") String orderBy,
            @RequestParam(value = "orientation", required = false, defaultValue = "") String orientation) {

        WrapperData data = this.expedienteService.findAll(page, size, startDate, endDate, identificator,number,reference,description,status,universalFilter,includeDocuments,orderBy,orientation);

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id,
                                           @RequestParam(value = "includeDocuments", required = false, defaultValue = "false") boolean includeDocuments) {
        ExpedienteDTO expediente = this.expedienteService.findById(id, includeDocuments);
        return new ResponseEntity<>(expediente, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/caratula")
    public ResponseEntity getCover(@PathVariable Long id){
        try {
            String coverPath = this.templateService.generateCover(id);
            return this.templateService.obtenerArchivoADescargar(coverPath);
        }catch (Exception e){
            log.error("Ocurrio un error en controlador de expedientes al intentar obtener caratula. Expcecion: {}",e);
            return new ResponseEntity<> ("Error al obtener caratula", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/tipos")
    public ResponseEntity<Object> findTypes(){
        return new ResponseEntity<>(Arrays.asList(TipoExpedienteEnum.values()), HttpStatus.OK);
    }

    @GetMapping(value = "/estados")
    public ResponseEntity<Object> findStatus(){
        return new ResponseEntity<>(Arrays.asList(EstadoExpedienteEnum.values()), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/cambiar-estado")
    public ResponseEntity<Object> updateStatus(@PathVariable Long id, @RequestBody StatusDTO status){
        this.expedienteService.updateStatus(id, status.getStatus());
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

}
