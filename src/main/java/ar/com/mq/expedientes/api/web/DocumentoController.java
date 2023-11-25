package ar.com.mq.expedientes.api.web;

import ar.com.mq.expedientes.api.model.dto.DocumentoDTO;
import ar.com.mq.expedientes.api.service.interfaces.DocumentoService;
import ar.com.mq.expedientes.core.constants.SwaggerTags;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin(origins = {"http://localhost:5173", "*"}, maxAge = 3600)
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

    @GetMapping(value = "/{name}")
    public ResponseEntity<Object> getDocument(@PathVariable String name){
        DocumentoDTO document = this.documentoService.findByName(name);
        try {
            Path imagePath = Paths.get(document.getUbicacion(), document.getNombre());
            Resource resource = new UrlResource(imagePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.parseMediaType(document.getTipoArchivo()).toString()) // Cambia el tipo MIME según el formato de la imagen
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).build(); // Manejo de errores
        }
    }
}
