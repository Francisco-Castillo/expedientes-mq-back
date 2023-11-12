package ar.com.mq.expedientes.api.service.interfaces;

import java.util.List;

import ar.com.mq.expedientes.api.model.dto.AreaDTO;

public interface AreaService {

    void save (AreaDTO area);

    List<AreaDTO> findAll(int page, int size, String search, String orderBy, String orientation);

}
