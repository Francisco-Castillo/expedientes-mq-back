package ar.com.mq.expedientes.api.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.mq.expedientes.api.model.dto.PaseDTO;
import ar.com.mq.expedientes.api.model.entity.Expediente;
import ar.com.mq.expedientes.api.model.entity.Pase;
import ar.com.mq.expedientes.api.model.mapper.interfaces.PaseMapper;
import ar.com.mq.expedientes.api.service.interfaces.PaseService;
import ar.com.mq.expedientes.api.service.repository.ExpedienteRepository;
import ar.com.mq.expedientes.api.service.repository.PaseRepository;
import ar.com.mq.expedientes.core.exception.exceptions.MunicipalidadMQRuntimeException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaseServiceImpl implements PaseService {

	private final PaseRepository paseRepository;
	private final ExpedienteRepository expedienteRepository;
	private final PaseMapper paseMapper;

	@Autowired
	public PaseServiceImpl(PaseRepository paseRepository, ExpedienteRepository expedienteRepository,
			PaseMapper paseMapper) {
		this.paseRepository = paseRepository;
		this.expedienteRepository = expedienteRepository;
		this.paseMapper = paseMapper;
	}

	public PaseDTO save(PaseDTO pase) {
		log.info("Por registrar pase: {}", pase);
		try {
			// TODO: Validar datos de expediente.
			Pase paseSaved = this.paseRepository.save(this.paseMapper.toEntity(pase));
			return this.paseMapper.toDTO(paseSaved);
		} catch (Exception e) {
			String message = String
					.format("Ocurrio un error al intentar realizar pase de expediente: %s. Excepcion: %s", pase, e);
			log.error(message);
			throw MunicipalidadMQRuntimeException.conflictException(message);
		}
	}

	@Override
	public List<PaseDTO> findAllPases(Long id) {
		
		Optional<Expediente> expediente = this.expedienteRepository.findById(id);
		
		if (expediente.isEmpty()) {
			throw MunicipalidadMQRuntimeException.notFoundException("No se encontro expediente");
		}
		
		expediente.get().getPases().sort(Comparator.comparingLong(Pase::getId).reversed());
		
		return this.paseMapper.toListDTO(expediente.get().getPases());
	}

}
