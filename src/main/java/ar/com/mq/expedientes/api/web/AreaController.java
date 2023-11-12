package ar.com.mq.expedientes.api.web;

import ar.com.mq.expedientes.api.model.dto.AreaDTO;
import ar.com.mq.expedientes.api.service.interfaces.AreaService;
import ar.com.mq.expedientes.core.constants.SwaggerTags;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/areas")
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600)
@Slf4j
@Api(tags = {SwaggerTags.AREA_TAG})
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
    public ResponseEntity<Object> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "orderBy", required = false, defaultValue = "") String orderBy,
            @RequestParam(value = "orientation", required = false, defaultValue = "") String orientation,
            @RequestParam(value = "search", required = false, defaultValue = "") String search) {

        return new ResponseEntity<>(this.areaService.findAll(page, size, search, orderBy, orientation), HttpStatus.OK);
    }
}
