package ar.com.mq.expedientes.api.service.impl;

import ar.com.mq.expedientes.api.model.dto.ExpedienteDTO;
import ar.com.mq.expedientes.api.model.dto.ExpedientePagoDTO;
import ar.com.mq.expedientes.api.model.dto.WrapperData;
import ar.com.mq.expedientes.api.model.entity.Expediente;
import ar.com.mq.expedientes.api.model.entity.ExpedientePago;
import ar.com.mq.expedientes.api.model.mapper.interfaces.ExpedienteMapper;
import ar.com.mq.expedientes.api.model.mapper.interfaces.ExpedientePagoMapper;
import ar.com.mq.expedientes.api.service.interfaces.ExpedienteService;
import ar.com.mq.expedientes.api.service.repository.ExpedienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ExpedienteServiceImpl implements ExpedienteService {

    private final ExpedienteRepository expedienteRepository;
    private final ExpedientePagoMapper expedientePagoMapper;
    private final ExpedienteMapper expedienteMapper;

    @Autowired
    public ExpedienteServiceImpl(ExpedienteRepository expedienteRepository, ExpedientePagoMapper expedientePagoMapper, ExpedienteMapper expedienteMapper) {
        this.expedienteRepository = expedienteRepository;
        this.expedientePagoMapper = expedientePagoMapper;
        this.expedienteMapper = expedienteMapper;
    }

    @Override
    public void save(ExpedientePagoDTO expediente) {
        ExpedientePago expedientePago = this.expedientePagoMapper.toEntity(expediente);
        this.expedienteRepository.save(expedientePago);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void update(ExpedienteDTO expediente) {

    }

    @Override
    public WrapperData findAll(int page, int size, String search, String orderBy, String orientation) {

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Expediente> expedientePage = expedienteRepository.findAll(new Specification<Expediente>() {

            @Override
            public Predicate toPredicate(Root<Expediente> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (StringUtils.isNotBlank(search)) {
                    predicates.add(cb.or(
                            cb.like(cb
                                    .lower(root.get("numero")), "%" + search.toLowerCase() + "%"),
                            cb.like(cb
                                    .lower(root.get("referencia")), "%" + search.toLowerCase() + "%"),
                            cb.like(cb
                                    .lower(root.get("descripcion")), "%" + search.toLowerCase() + "%"),
                            cb.like(cb
                                    .lower(root.get("codigoTramite")), "%" + search.toLowerCase() + "%"))
                    );
                }


                // Ordenamos
                if (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(orientation)) {

                    cq.orderBy(orientation.equals("asc")
                            ? cb.asc(root.get(orderBy))
                            : cb.desc(root.get(orderBy)));

                } else {
                    cq.orderBy(cb.asc(root.get("fechaCaratulacion")));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }

        }, pageRequest);

        List<ExpedienteDTO> expedientes = this.expedienteMapper.toListDTO(expedientePage.getContent());

        WrapperData<ExpedienteDTO> wrapperData = new WrapperData<>();

        wrapperData.setItems(expedientes);
        wrapperData.setTotalItems(expedientePage.getTotalElements());
        wrapperData.setTotalPages(expedientePage.getTotalPages());
        wrapperData.setCurrentPage(expedientePage.getNumber());

        return wrapperData;

    }
}
