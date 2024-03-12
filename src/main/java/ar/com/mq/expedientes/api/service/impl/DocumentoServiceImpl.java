package ar.com.mq.expedientes.api.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import ar.com.mq.expedientes.api.model.dto.DocumentoDTO;
import ar.com.mq.expedientes.api.model.dto.ExpedienteDTO;
import ar.com.mq.expedientes.api.model.entity.Documento;
import ar.com.mq.expedientes.api.model.mapper.interfaces.DocumentoMapper;
import ar.com.mq.expedientes.api.service.interfaces.DocumentoService;
import ar.com.mq.expedientes.api.service.interfaces.ExpedienteService;
import ar.com.mq.expedientes.api.service.interfaces.ParametroService;
import ar.com.mq.expedientes.api.service.repository.DocumentRepository;
import ar.com.mq.expedientes.core.exception.exceptions.MunicipalidadMQRuntimeException;
import ar.com.mq.expedientes.core.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DocumentoServiceImpl implements DocumentoService {

	private final DocumentRepository documentRepository;
	private final DocumentoMapper documentoMapper;
	private final ExpedienteService expedienteService;

	private final ParametroService parametroService;

	private ObjectMapper objectMapper; // Hacer implementacion propia / abstraccion.

	@Autowired
	public DocumentoServiceImpl(DocumentRepository documentRepository, DocumentoMapper documentoMapper,
			ExpedienteService expedienteService, ParametroService parametroService, ObjectMapper objectMapper) {
		this.documentRepository = documentRepository;
		this.documentoMapper = documentoMapper;
		this.expedienteService = expedienteService;
		this.parametroService = parametroService;
		this.objectMapper = objectMapper;
	}

	@Override
	public void save(HttpServletRequest request, String data) {

		if (StringUtils.isBlank(data))
			throw MunicipalidadMQRuntimeException.badRequestException("El campo data es obligatorio.");

		try {
			DocumentoDTO documento = this.objectMapper.readValue(data, DocumentoDTO.class); // Revisar tema de
																							// validacion.

			List<MultipartFile> files = FileUtils.getMultipartFiles("file", request);

			if (CollectionUtils.isNotEmpty(files)) {

				var directorioDeAlmacenamiento = this.parametroService.getDirectorioDeAlmacenamiento();

				ExpedienteDTO expediente = this.expedienteService.findById(documento.getExpedienteId(), false);

				String ubicacion = directorioDeAlmacenamiento + "/" + expediente.getNumero() + "/";
				documento.setUbicacion(ubicacion);
				documento.setNombre(files.get(0).getOriginalFilename());
				documento.setTipoArchivo(files.get(0).getContentType());

				Documento entity = this.documentoMapper.toEntity(documento);

				this.documentRepository.save(entity);

				FileUtils.createDirectory(directorioDeAlmacenamiento + "/" + expediente.getNumero());

				FileUtils.saveMultipleFiles(files, directorioDeAlmacenamiento + "/" + expediente.getNumero() + "/");

				log.info("Documento guardado exitosamente {}", entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public DocumentoDTO findByName(String name) {
		Documento document = this.documentRepository.findByNombre(name);
		if (ObjectUtils.isEmpty(document)) {
			throw MunicipalidadMQRuntimeException.notFoundException("No se encontro documento");
		}
		return this.documentoMapper.toDTO(document);
	}

	@Override
	public List<DocumentoDTO> findAllByExpedienteId(Long expedienteId) {
		List<Documento> list = this.documentRepository.findAllByExpedienteId(expedienteId);
		return this.documentoMapper.toListDTO(list);
	}
}
