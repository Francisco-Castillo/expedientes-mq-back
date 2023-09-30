package ar.com.mq.expedientes.api.service.impl;

import ar.com.mq.expedientes.api.enums.ParametroEnum;
import ar.com.mq.expedientes.api.model.dto.ExpedienteDTO;
import ar.com.mq.expedientes.api.model.dto.ExpedientePagoDTO;
import ar.com.mq.expedientes.api.model.dto.WrapperData;
import ar.com.mq.expedientes.api.model.entity.Expediente;
import ar.com.mq.expedientes.api.model.entity.ExpedientePago;
import ar.com.mq.expedientes.api.model.mapper.interfaces.ExpedienteMapper;
import ar.com.mq.expedientes.api.model.mapper.interfaces.ExpedientePagoMapper;
import ar.com.mq.expedientes.api.service.interfaces.ExpedienteService;
import ar.com.mq.expedientes.api.service.interfaces.ParametroService;
import ar.com.mq.expedientes.api.service.repository.ExpedienteRepository;
import ar.com.mq.expedientes.core.exception.exceptions.MunicipalidadMQRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ExpedienteServiceImpl implements ExpedienteService {

    private final ExpedienteRepository expedienteRepository;
    private final ExpedientePagoMapper expedientePagoMapper;
    private final ExpedienteMapper expedienteMapper;
    private final ParametroService parametroService;

    @Autowired
    public ExpedienteServiceImpl(ExpedienteRepository expedienteRepository, ExpedientePagoMapper expedientePagoMapper, ExpedienteMapper expedienteMapper, ParametroService parametroService) {
        this.expedienteRepository = expedienteRepository;
        this.expedientePagoMapper = expedientePagoMapper;
        this.expedienteMapper = expedienteMapper;
        this.parametroService = parametroService;
    }

    @Override
    public void save(ExpedientePagoDTO expediente) {

        String number = expediente.getNumero();

        if (expediente.getNumero().contains("-")) {
            int middleDashPosition = expediente.getNumero().indexOf("-");
            number = expediente.getNumero().substring(0, middleDashPosition);
        }

        ExpedientePago expedientePago = this.expedientePagoMapper.toEntity(expediente);

        this.expedienteRepository.save(expedientePago);

        this.parametroService.updateValue(ParametroEnum.ULTIMO_NUMERO_DE_EXPEDIENTE.getValue(), number);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void update(Long id, ExpedienteDTO expediente) {

        if (!expedienteRepository.existsById(id))
            throw MunicipalidadMQRuntimeException.notFoundException("No se encontro expediente");

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

    @Override
    public ExpedienteDTO findById(Long id) {
        return this.expedienteRepository.findById(id).map(this.expedienteMapper::toDTO)
                .orElseThrow(() -> MunicipalidadMQRuntimeException.notFoundException("No se encontro expediente"));
    }
}
