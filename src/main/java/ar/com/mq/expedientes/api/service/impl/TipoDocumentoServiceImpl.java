package ar.com.mq.expedientes.api.service.impl;

import ar.com.mq.expedientes.api.model.dto.TipoDocumentoDTO;
import ar.com.mq.expedientes.api.model.dto.WrapperData;
import ar.com.mq.expedientes.api.model.entity.TipoDocumento;
import ar.com.mq.expedientes.api.model.mapper.interfaces.TipoDocumentoMapper;
import ar.com.mq.expedientes.api.service.interfaces.TipoDocumentoService;
import ar.com.mq.expedientes.api.service.repository.TipoDocumentoRepository;
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
public class TipoDocumentoServiceImpl implements TipoDocumentoService {

    private final TipoDocumentoMapper tipoDocumentoMapper;
    private final TipoDocumentoRepository tipoDocumentoRepository;

    @Autowired
    public TipoDocumentoServiceImpl(TipoDocumentoMapper tipoDocumentoMapper, TipoDocumentoRepository tipoDocumentoRepository) {
        this.tipoDocumentoMapper = tipoDocumentoMapper;
        this.tipoDocumentoRepository = tipoDocumentoRepository;
    }

    @Override
    public void save(TipoDocumentoDTO tipoDocumentoDTO) {
        TipoDocumento entity = this.tipoDocumentoMapper.toEntity(tipoDocumentoDTO);
        this.tipoDocumentoRepository.save(entity);
    }

    @Override
    public WrapperData findAll(int page, int size, String search, String orderBy, String orientation) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<TipoDocumento> tipoDocumentoPage = tipoDocumentoRepository.findAll(new Specification<TipoDocumento>() {

            @Override
            public Predicate toPredicate(Root<TipoDocumento> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (StringUtils.isNotBlank(search)) {
                    predicates.add(cb.like(cb.lower(root.get("descripcion")), "%" + search.toLowerCase() + "%"));
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

        List<TipoDocumentoDTO> tipos = this.tipoDocumentoMapper.toListDTO(tipoDocumentoPage.getContent());

        WrapperData<TipoDocumentoDTO> wrapperData = new WrapperData<>();

        wrapperData.setItems(tipos);
        wrapperData.setTotalItems(tipoDocumentoPage.getTotalElements());
        wrapperData.setTotalPages(tipoDocumentoPage.getTotalPages());
        wrapperData.setCurrentPage(tipoDocumentoPage.getNumber());

        return wrapperData;
    }
}
