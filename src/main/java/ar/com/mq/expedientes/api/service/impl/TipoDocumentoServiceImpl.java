package ar.com.mq.expedientes.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import ar.com.mq.expedientes.api.model.dto.TipoDocumentoDTO;
import ar.com.mq.expedientes.api.model.entity.TipoDocumento;
import ar.com.mq.expedientes.api.model.mapper.interfaces.TipoDocumentoMapper;
import ar.com.mq.expedientes.api.service.interfaces.TipoDocumentoService;
import ar.com.mq.expedientes.api.service.repository.TipoDocumentoRepository;

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
    public List<TipoDocumentoDTO> findAll(int page, int size, String search, String orderBy, String orientation) {
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

        return this.tipoDocumentoMapper.toListDTO(tipoDocumentoPage.getContent());

    }
}
