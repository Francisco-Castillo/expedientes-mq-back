package ar.com.mq.expedientes.api.service.interfaces;

import java.util.List;

import ar.com.mq.expedientes.api.model.dto.TipoExpedienteDTO;
import ar.com.mq.expedientes.api.model.entity.TipoExpediente;

public interface TipoExpedienteService {

	List<TipoExpedienteDTO> findAll(int page, int size, String search, String orderBy, String orientation);

	TipoExpedienteDTO create(TipoExpedienteDTO expediente);

	TipoExpediente findById(Integer expedienteId);

	TipoExpedienteDTO update(TipoExpedienteDTO expediente, Integer expedienteId);

	void delete(Integer expedienteId);

}
