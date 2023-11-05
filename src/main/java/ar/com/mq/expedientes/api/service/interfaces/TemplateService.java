package ar.com.mq.expedientes.api.service.interfaces;

import ar.com.mq.expedientes.api.model.dto.TemplateDTO;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface TemplateService {

    TemplateDTO findByType(String type);

    String generateCover(Long expedienteId) throws Exception;

    ResponseEntity<ByteArrayResource> obtenerArchivoADescargar(String pathPdf) throws IOException;
}
