package ar.com.mq.expedientes.api.service.impl;

import ar.com.mq.expedientes.api.model.dto.AreaDTO;
import ar.com.mq.expedientes.api.model.dto.UsuarioBaseDTO;
import ar.com.mq.expedientes.api.model.dto.WrapperData;
import ar.com.mq.expedientes.api.model.entity.Area;
import ar.com.mq.expedientes.api.model.entity.Usuario;
import ar.com.mq.expedientes.api.model.mapper.interfaces.AreaMapper;
import ar.com.mq.expedientes.api.service.interfaces.AreaService;
import ar.com.mq.expedientes.api.service.repository.AreaRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public WrapperData findAll(int page, int size, String search, String orderBy, String orientation) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Area> areaPage = areaRepository.findAll(new Specification<Area>() {
            @Override
            public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (StringUtils.isNotBlank(search)) {
                    predicates.add(cb.or(
                            cb.like(cb
                                    .lower(root.get("descripcion")), "%" + search.toLowerCase() + "%"))
                    );
                }

                // Ordenamos
                if (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(orientation)) {

                    cq.orderBy(orientation.equals("asc")
                            ? cb.asc(root.get(orderBy))
                            : cb.desc(root.get(orderBy)));

                } else {
                    cq.orderBy(cb.asc(root.get("descripcion")));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }

        }, pageRequest);

        List<AreaDTO> areas = this.areaMapper.toListDTO(areaPage.getContent());

        WrapperData<AreaDTO> wrapperData = new WrapperData<>();

        wrapperData.setItems(areas);
        wrapperData.setTotalItems(areaPage.getTotalElements());
        wrapperData.setTotalPages(areaPage.getTotalPages());
        wrapperData.setCurrentPage(areaPage.getNumber());

        return wrapperData;
    }
}
