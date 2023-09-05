package ar.com.mq.expedientes.api.service.interfaces;

import ar.com.mq.expedientes.api.model.dto.TipoDocumentoDTO;
import ar.com.mq.expedientes.api.model.dto.WrapperData;

public interface TipoDocumentoService {

    void save (TipoDocumentoDTO tipoDocumentoDTO);

    WrapperData findAll(int page, int size, String search, String orderBy, String orientation);
}
