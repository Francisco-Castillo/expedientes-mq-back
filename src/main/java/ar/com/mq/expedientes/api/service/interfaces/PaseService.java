package ar.com.mq.expedientes.api.service.interfaces;

import java.util.List;

import ar.com.mq.expedientes.api.model.dto.PaseDTO;

public interface PaseService {
	
	PaseDTO save(PaseDTO entity);
	
	List<PaseDTO> findAllPases(Long id);

}
