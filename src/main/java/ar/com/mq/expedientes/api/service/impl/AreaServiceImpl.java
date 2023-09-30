package ar.com.mq.expedientes.api.service.impl;

import ar.com.mq.expedientes.api.model.dto.AreaDTO;
import ar.com.mq.expedientes.api.model.entity.Area;
import ar.com.mq.expedientes.api.model.mapper.interfaces.AreaMapper;
import ar.com.mq.expedientes.api.service.interfaces.AreaService;
import ar.com.mq.expedientes.api.service.repository.AreaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AreaServiceImpl implements AreaService {

    private final AreaMapper areaMapper;
    private final AreaRepository areaRepository;

    @Autowired
    public AreaServiceImpl(AreaMapper areaMapper, AreaRepository areaRepository) {
        this.areaMapper = areaMapper;
        this.areaRepository = areaRepository;
    }

    @Override
    public void save(AreaDTO area) {
        log.debug("Por guardar area: {}", area);
        Area entity = this.areaMapper.toEntity(area);
        this.areaRepository.save(entity);
    }
}
