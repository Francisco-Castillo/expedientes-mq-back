package ar.com.mq.expedientes.api.service.interfaces;

import ar.com.mq.expedientes.api.model.dto.AreaDTO;
import ar.com.mq.expedientes.api.model.dto.WrapperData;

public interface AreaService {

    void save (AreaDTO area);

    WrapperData findAll(int page, int size, String search, String orderBy, String orientation);

}
