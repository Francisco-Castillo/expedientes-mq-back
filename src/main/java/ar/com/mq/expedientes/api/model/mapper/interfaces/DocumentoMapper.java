package ar.com.mq.expedientes.api.model.mapper.interfaces;

import ar.com.mq.expedientes.api.model.dto.DocumentoDTO;
import ar.com.mq.expedientes.api.model.entity.Documento;
import ar.com.mq.expedientes.core.business.MunicipalidadMQMapper;

public interface DocumentoMapper extends MunicipalidadMQMapper<Documento, DocumentoDTO> {
}
