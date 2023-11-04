package ar.com.mq.expedientes.api.service.impl;

import ar.com.mq.expedientes.api.enums.ParametroEnum;
import ar.com.mq.expedientes.api.model.dto.ParametroDTO;
import ar.com.mq.expedientes.api.model.mapper.interfaces.ParametroMapper;
import ar.com.mq.expedientes.api.service.interfaces.ParametroService;
import ar.com.mq.expedientes.api.service.repository.ParametroRepository;
import ar.com.mq.expedientes.core.exception.exceptions.MunicipalidadMQRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ParametroServiceImpl implements ParametroService {

    private final ParametroRepository parametroRepository;
    private final ParametroMapper parametroMapper;

    @Autowired
    public ParametroServiceImpl(ParametroRepository parametroRepository, ParametroMapper parametroMapper) {
        this.parametroRepository = parametroRepository;
        this.parametroMapper = parametroMapper;
    }

    @Override
    public String getDirectorioDeAlmacenamiento() {
        try {
            String parameter =  this.parametroRepository.getValor(ParametroEnum.DIRECTORIO_DE_ALMACENAMIENTO.getValue());
            if (StringUtils.isBlank(parameter)) throw MunicipalidadMQRuntimeException.notFoundException("No se encontro parametro "+ParametroEnum.DIRECTORIO_DE_ALMACENAMIENTO);
            return parameter;
        } catch (Exception e) {
            log.error("No se encontro parametro {}", ParametroEnum.DIRECTORIO_DE_ALMACENAMIENTO.getValue());
            throw MunicipalidadMQRuntimeException.notFoundException("No se encontro parametro");
        }
    }

    @Override
    public ParametroDTO findByNombre(String nombre) {
        log.info("Buscando parametro {}", nombre);
        try {
            return this.parametroMapper.toDTO(this.parametroRepository.findByNombre(nombre));
        } catch (Exception e) {
            log.error("No se encontro parametro {}", nombre);
            throw MunicipalidadMQRuntimeException.notFoundException("No se encontro parametro");
        }
    }

    @Override
    public void updateValue(String name, String value) {
        log.info("Por actualizar parametro {} con el valor {}", name, value);
        this.parametroRepository.updateValue(name, value);
    }


}
