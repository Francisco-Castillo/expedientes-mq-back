package ar.com.mq.expedientes.api.model.mapper.interfaces;

import ar.com.mq.expedientes.api.model.dto.ExpedienteDTO;
import ar.com.mq.expedientes.api.model.entity.Expediente;
import ar.com.mq.expedientes.core.business.MunicipalidadMQMapper;

public interface ExpedienteMapper extends MunicipalidadMQMapper<Expediente, ExpedienteDTO> {
}
