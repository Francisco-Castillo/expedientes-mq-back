package ar.com.mq.expedientes.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.mq.expedientes.api.model.dto.AreaDTO;
import ar.com.mq.expedientes.api.service.interfaces.AreaService;
import ar.com.mq.expedientes.core.constants.SwaggerTags;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/areas")
//@CrossOrigin(origins = {"http://localhost:5173", "*"}, maxAge = 3600)
@CrossOrigin(origins = { "http://vps-4188220-x.dattaweb.com", "*" }, maxAge = 3600)
@Slf4j
@Api(tags = { SwaggerTags.AREA_TAG })
public class AreaController {
	private final AreaService areaService;

	@Autowired
	public AreaController(AreaService areaService) {
		this.areaService = areaService;
	}

	@PostMapping
	public ResponseEntity<Object> save(@RequestBody AreaDTO area) {
		log.debug("Por guardar area: {}", area);
		this.areaService.save(area);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<Object> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size,
			@RequestParam(value = "orderBy", required = false, defaultValue = "") String orderBy,
			@RequestParam(value = "orientation", required = false, defaultValue = "") String orientation,
			@RequestParam(value = "search", required = false, defaultValue = "") String search) {

		return new ResponseEntity<>(this.areaService.findAll(page, size, search, orderBy, orientation), HttpStatus.OK);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> update(@RequestBody AreaDTO dto, @PathVariable Long id) {
		AreaDTO area = this.areaService.update(dto, id);
		return new ResponseEntity<>("Area actualizada exitosamente", HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		this.areaService.delete(id);
		return new ResponseEntity<>("Area eliminada exitosamente", HttpStatus.OK);
	}
}
