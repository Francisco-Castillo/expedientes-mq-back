package ar.com.mq.expedientes.api.service.interfaces;

import java.util.List;

import ar.com.mq.expedientes.api.model.dto.TipoExpedienteDTO;

public interface TipoExpedienteService {

	List<TipoExpedienteDTO> findAll(int page, int size, String search, String orderBy, String orientation);

}
