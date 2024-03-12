package ar.com.mq.expedientes.api.service.interfaces;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ar.com.mq.expedientes.api.model.dto.DocumentoDTO;

public interface DocumentoService {

	void save(HttpServletRequest request, String data);

	DocumentoDTO findByName(String name);

	List<DocumentoDTO> findAllByExpedienteId(Long expedienteId);
}
