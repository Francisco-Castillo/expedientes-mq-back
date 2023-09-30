package ar.com.mq.expedientes.api.web;

import ar.com.mq.expedientes.api.model.dto.AreaDTO;
import ar.com.mq.expedientes.api.service.interfaces.AreaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/areas")
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600)
@Slf4j
public class AreaController {
    private final AreaService areaService;

    @Autowired
    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody AreaDTO area) {
        this.areaService.save(area);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
