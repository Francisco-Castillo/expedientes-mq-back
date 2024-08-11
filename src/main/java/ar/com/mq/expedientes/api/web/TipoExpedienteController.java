package ar.com.mq.expedientes.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.mq.expedientes.api.model.dto.TipoExpedienteDTO;
import ar.com.mq.expedientes.api.service.interfaces.TipoExpedienteService;
import ar.com.mq.expedientes.core.constants.SwaggerTags;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/tipos-expedientes")
//@CrossOrigin(origins = {"http://localhost:5173", "*"}, maxAge = 3600)
@CrossOrigin(origins = { "http://vps-4188220-x.dattaweb.com", "*" }, maxAge = 3600)

@Slf4j
@Api(tags = { SwaggerTags.TIPOS_DOCUMENTOS_TAG })
public class TipoExpedienteController {

	private final TipoExpedienteService tipoExpedienteService;

	@Autowired
	public TipoExpedienteController(TipoExpedienteService tipoExpedienteService) {
		this.tipoExpedienteService = tipoExpedienteService;
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody TipoExpedienteDTO dto) {
		TipoExpedienteDTO tipoSaved = this.tipoExpedienteService.create(dto);
		return new ResponseEntity<>("Tipo de expediente registrado exitosamente", HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> update(@RequestBody TipoExpedienteDTO dto, @PathVariable Integer id) {
		TipoExpedienteDTO tipoSaved = this.tipoExpedienteService.update(dto, id);
		return new ResponseEntity<>("Tipo de expediente actualizado exitosamente", HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> delete(@PathVariable Integer id) {
		this.tipoExpedienteService.delete(id);
		return new ResponseEntity<>("Tipo de expediente eliminado exitosamente", HttpStatus.OK);
	}

}
