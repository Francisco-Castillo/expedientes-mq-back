package ar.com.mq.expedientes.api.service.interfaces;

import ar.com.mq.expedientes.api.model.dto.DocumentoDTO;

import javax.servlet.http.HttpServletRequest;

public interface DocumentoService {

    void save(HttpServletRequest request, String data);
}
