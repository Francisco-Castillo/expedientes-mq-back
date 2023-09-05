package ar.com.mq.expedientes.api.service.interfaces;

import ar.com.mq.expedientes.api.model.dto.ExpedienteDTO;
import ar.com.mq.expedientes.api.model.dto.ExpedientePagoDTO;
import ar.com.mq.expedientes.api.model.dto.WrapperData;

public interface ExpedienteService {

    void save(ExpedientePagoDTO expediente);

    void deleteById(Long id);

    void update(ExpedienteDTO expediente);

    WrapperData findAll(int page, int size, String search, String orderBy, String orientation);


}
