package ar.com.mq.expedientes.api.service.impl;

import ar.com.mq.expedientes.api.enums.TemplateEnum;
import ar.com.mq.expedientes.api.model.dto.ExpedienteDTO;
import ar.com.mq.expedientes.api.model.dto.TemplateDTO;
import ar.com.mq.expedientes.api.model.mapper.interfaces.TemplateMapper;
import ar.com.mq.expedientes.api.service.interfaces.ExpedienteService;
import ar.com.mq.expedientes.api.service.interfaces.TemplateService;
import ar.com.mq.expedientes.api.service.repository.TemplateRepository;
import ar.com.mq.expedientes.core.exception.exceptions.MunicipalidadMQRuntimeException;
import io.woo.htmltopdf.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Service
@Slf4j
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;
    private final TemplateMapper templateMapper;

    private final ExpedienteService expedienteService;

    @Value("${temporalPath}")
    private String temporalPath;

    @Autowired
    public TemplateServiceImpl(TemplateRepository templateRepository, TemplateMapper templateMapper, ExpedienteService expedienteService) {
        this.templateRepository = templateRepository;
        this.templateMapper = templateMapper;
        this.expedienteService = expedienteService;
    }

    @Override
    public TemplateDTO findByType(String type) {
        return this.templateMapper.toDTO(this.templateRepository.findByTipo(type));
    }

    @Override
    public String generateCover(Long expedienteId) throws Exception{

        // Obtener logo mmq en base 64

        String logoMMQ = encodeFileToBase64(temporalPath.concat("/mmq.png"));

        // Leer template HTML.
        TemplateDTO template = this.findByType(TemplateEnum.CARATULA.getValue());

        // Buscamos datos de expediente.
        ExpedienteDTO expediente = this.expedienteService.findById(expedienteId, false);

        VelocityContext context = new VelocityContext();

        // Completar parametros de template con datos del expediente.

        context.put("cid", logoMMQ);
        context.put("expedienteNumero",expediente.getNumero());
        context.put("fecha", expediente.getFechaCaratulacion());
        context.put("iniciador", "Secretaria de Gobierno");
        context.put("extracto", expediente.getReferencia());

        StringWriter writer = new StringWriter();

        Velocity.evaluate(context, writer, "EvalError", template.getContent());

        String response = writer.toString();

        byte[] bytes = convertHTMLToPDF(response, PdfOrientation.LANDSCAPE, PdfPageSize.A6);


        FileOutputStream fileOutputStream = null;
        try {

            //String fileName = PREFIX.concat(bookDTO.getIdentification()).concat(EXTENSION);
            String fileName = "caratula-".concat(expediente.getNumero()).concat(".pdf");

            File file = prepararDirectorioYFile(temporalPath, fileName);

            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
            fileOutputStream.flush();

            return file.getAbsolutePath();
        }catch (Exception e){
            throw e;
        }finally {
            fileOutputStream.close();
        }

    }


    private byte[] convertHTMLToPDF(String contentHtml, PdfOrientation orientation, PdfPageSize size) {
        HtmlToPdf htmlToPdf = HtmlToPdf.create()
                .object(HtmlToPdfObject.forHtml(contentHtml)
                        .defaultEncoding("UTF-8")
                        .loadImages(true)
                        .useExternalLinks(true));

        if (ObjectUtils.isNotEmpty(orientation)) {
            htmlToPdf.orientation(orientation);
        }

        if (ObjectUtils.isNotEmpty(size)) {
            htmlToPdf.pageSize(size);
        }

        try (InputStream in = htmlToPdf.convert()) {
            return IOUtils.toByteArray(in);
        } catch (Exception e) {
            throw MunicipalidadMQRuntimeException.conflictException("Error al convertir html to pdf "+e.getLocalizedMessage());
        }

    }

    private File prepararDirectorioYFile(String pathS, String fileName) throws Exception {

        File file = new File(pathS + File.separator + fileName);
        try {
            File path = new File(pathS);
            if (!path.exists()) {
                boolean mkdirsResult = path.mkdirs();
                if (!mkdirsResult) {
                    log.error("Hubo un problema al generar el directorio: " + pathS);
                }
            }
            if (file.exists()) {
                boolean delResult = file.delete();
                if (!delResult) {
                    log.error("Hubo un problema al borrar el archivo" + file.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            String msg = String.format("Hubo un problema al generar el directorio [%s] o borrar el archivo [%s]. La causa de la exception es: %s",
                    pathS, pathS + File.separator + fileName, e.getMessage()
            );
            log.error(msg, e);
            throw new Exception(msg, e);
        }
        return file;
    }

    @Override
    public ResponseEntity<ByteArrayResource> obtenerArchivoADescargar(String pathPdf) throws IOException {
        Path path = Paths.get(pathPdf);
        byte[] data = Files.readAllBytes(path);
        ByteArrayResource resource = new ByteArrayResource(data);
        MediaType mediaType = MediaType.parseMediaType("application/pdf");
        return ResponseEntity.ok()
                // Content-Disposition
                .header("Content-Disposition", "attachment;filename=" + path.getFileName().toString())
                // Content-Type
                .contentType(mediaType) //
                // Content-Lengh
                .contentLength(data.length) //
                .body(resource);
    }

    private static String encodeFileToBase64(String fileName) throws IOException{
        // Lee la imagen desde el directorio
        byte[] imagenBytes = IOUtils.toByteArray(new FileInputStream(fileName));

        // Convierte la imagen a base64
        String imagenBase64 = Base64.getEncoder().encodeToString(imagenBytes);

        return "data:image/jpeg;base64, ".concat(imagenBase64);
    }
}
