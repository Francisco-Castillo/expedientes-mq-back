package ar.com.mq.expedientes.api.service.interfaces;

import java.time.LocalDate;
import java.util.List;

import ar.com.mq.expedientes.api.model.dto.ExpedienteDTO;
import ar.com.mq.expedientes.api.model.dto.WrapperData;

public interface ExpedienteService {

	void save(ExpedienteDTO expediente);

	void deleteById(Long id);

	void update(Long id, ExpedienteDTO expediente);

	WrapperData findAll(Integer page, Integer size, LocalDate startDate, LocalDate endDate, String identificator,
			String number, String reference, String description, String status, String caratulador, Long caratuladorId,
			String universalFilter, boolean includeDocuments, String orderBy, String orientation);

	ExpedienteDTO findById(Long id, boolean includeDocument);

	void updateStatus(Long id, String status);

	/**
	 * Método encargado de retornar un listado de expedientes cuyo destino soy yo
	 * como receptor.
	 * 
	 * @param usuarioReceptorId
	 * @return
	 */
	List<ExpedienteDTO> buscarTodosMisExpedientes(Long usuarioReceptorId);

}
