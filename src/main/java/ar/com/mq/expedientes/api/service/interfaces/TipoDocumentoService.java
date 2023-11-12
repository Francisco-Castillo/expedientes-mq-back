package ar.com.mq.expedientes.api.service.interfaces;

import java.util.List;

import ar.com.mq.expedientes.api.model.dto.TipoDocumentoDTO;

public interface TipoDocumentoService {

    void save (TipoDocumentoDTO tipoDocumentoDTO);

    List<TipoDocumentoDTO> findAll(int page, int size, String search, String orderBy, String orientation);
}
