package ar.com.mq.expedientes.api.service.interfaces;

import ar.com.mq.expedientes.api.model.dto.ParametroDTO;
import ar.com.mq.expedientes.api.model.entity.Parametro;

public interface ParametroService {

    String getDirectorioDeAlmacenamiento();

    ParametroDTO findByNombre(String nombre);
}
