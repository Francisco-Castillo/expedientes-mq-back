package ar.com.mq.expedientes.api.service.interfaces;

import ar.com.mq.expedientes.api.model.dto.ExpedienteDTO;
import ar.com.mq.expedientes.api.model.dto.WrapperData;
import ar.com.mq.expedientes.api.model.entity.Expediente;

import java.time.LocalDate;

public interface ExpedienteService {

    void save(ExpedienteDTO expediente);

    void deleteById(Long id);

    void update(Long id,ExpedienteDTO expediente);

    WrapperData findAll(Integer page,
                        Integer size,
                        LocalDate startDate,
                        LocalDate endDate,
                        String identificator,
                        String number,
                        String reference,
                        String description,
                        String status,
                        String universalFilter,
                        boolean includeDocuments,
                        String orderBy,
                        String orientation);

    ExpedienteDTO findById(Long id, boolean includeDocument);

    void updateStatus(Long id, String status);


}
