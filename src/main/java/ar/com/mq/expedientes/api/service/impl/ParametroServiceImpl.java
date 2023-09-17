package ar.com.mq.expedientes.api.service.impl;

import ar.com.mq.expedientes.api.enums.ParametroEnum;
import ar.com.mq.expedientes.api.service.interfaces.ParametroService;
import ar.com.mq.expedientes.api.service.repository.ParametroRepository;
import ar.com.mq.expedientes.core.exception.exceptions.MunicipalidadMQRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ParametroServiceImpl implements ParametroService {

    private final ParametroRepository parametroRepository;

    @Autowired
    public ParametroServiceImpl(ParametroRepository parametroRepository) {
        this.parametroRepository = parametroRepository;
    }

    @Override
    public String getDirectorioDeAlmacenamiento() {
        try {
            return this.parametroRepository.getValor(ParametroEnum.DIRECTORIO_DE_ALMACENAMIENTO.getValue());
        }catch (Exception e){
            throw MunicipalidadMQRuntimeException.notFoundException("No se encontro parametro");
        }
    }
}
