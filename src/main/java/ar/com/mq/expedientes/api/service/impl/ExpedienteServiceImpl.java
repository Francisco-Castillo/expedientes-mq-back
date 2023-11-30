package ar.com.mq.expedientes.api.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import ar.com.mq.expedientes.api.enums.ParametroEnum;
import ar.com.mq.expedientes.api.model.dto.DocumentoDTO;
import ar.com.mq.expedientes.api.model.dto.ExpedienteDTO;
import ar.com.mq.expedientes.api.model.dto.UsuarioBaseDTO;
import ar.com.mq.expedientes.api.model.dto.UsuarioDTO;
import ar.com.mq.expedientes.api.model.dto.WrapperData;
import ar.com.mq.expedientes.api.model.entity.Documento;
import ar.com.mq.expedientes.api.model.entity.Expediente;
import ar.com.mq.expedientes.api.model.entity.Pase;
import ar.com.mq.expedientes.api.model.mapper.interfaces.DocumentoMapper;
import ar.com.mq.expedientes.api.model.mapper.interfaces.ExpedienteMapper;
import ar.com.mq.expedientes.api.service.interfaces.ExpedienteService;
import ar.com.mq.expedientes.api.service.interfaces.ParametroService;
import ar.com.mq.expedientes.api.service.repository.ExpedienteRepository;
import ar.com.mq.expedientes.api.service.repository.PaseRepository;
import ar.com.mq.expedientes.core.exception.exceptions.MunicipalidadMQRuntimeException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ExpedienteServiceImpl implements ExpedienteService {

    private final ExpedienteRepository expedienteRepository;
    private final PaseRepository paseRepository;
    private final ExpedienteMapper expedienteMapper;
    private final DocumentoMapper documentoMapper;
    private final ParametroService parametroService;

    @Value("${api.url}")
    private String apiUrl;

    @Autowired
    public ExpedienteServiceImpl(ExpedienteRepository expedienteRepository, ExpedienteMapper expedienteMapper,
                                 ParametroService parametroService, 
                                 DocumentoMapper documentoMapper,
                                 PaseRepository paseRepository) {
        this.expedienteRepository = expedienteRepository;
        this.expedienteMapper = expedienteMapper;
        this.parametroService = parametroService;
        this.documentoMapper = documentoMapper;
        this.paseRepository = paseRepository;
    }

    @Override
    public void save(ExpedienteDTO expedienteDTO) {

        String number = expedienteDTO.getNumero();

        if (ObjectUtils.isNotEmpty(this.expedienteRepository.findByNumero(expedienteDTO.getNumero()))){
            throw  MunicipalidadMQRuntimeException.conflictException("Ya existe un expediente caratulado con el numero "+expedienteDTO.getNumero());
        }

        if (expedienteDTO.getNumero().contains("-")) {
            int middleDashPosition = expedienteDTO.getNumero().indexOf("-");
            number = expedienteDTO.getNumero().substring(0, middleDashPosition);
        }

        Expediente expediente = this.expedienteMapper.toEntity(expedienteDTO);

        this.expedienteRepository.save(expediente);

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
    public WrapperData findAll(Integer page,
                               Integer size,
                               LocalDate startDate,
                               LocalDate endDate,
                               String identificator,
                               String number,
                               String reference,
                               String description,
                               String status,
                               String caratulador,
                               Long caratuladorId,
                               String universalFilter,
                               boolean includeDocuments,
                               String orderBy,
                               String orientation) {

        if (page == null) {
            page = 0;
        }

        if (size == null) {
            size = 10;
        }

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Expediente> expedientePage = expedienteRepository.findAll(new Specification<Expediente>() {

            @Override
            public Predicate toPredicate(Root<Expediente> root, CriteriaQuery<?> cq, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                // StartDate.
                if (ObjectUtils.isNotEmpty(startDate)) {
                    Predicate startDatePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("fechaCaratulacion"), startDate);
                    predicates.add(startDatePredicate);
                }

                // EndDate.
                if (ObjectUtils.isNotEmpty(endDate)) {
                    Predicate endDatePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("fechaCaratulacion"), endDate);
                    predicates.add(endDatePredicate);
                }

                // Identificador de expediente.
                if (StringUtils.isNotBlank(identificator)) {
                    Expression<String> id = root.get("id").as(String.class);
                    Predicate identificatordPredicate = criteriaBuilder.like(id, "%" + identificator.trim() + "%");
                    predicates.add(identificatordPredicate);
                }

                // Numero de expediente.
                if (StringUtils.isNotBlank(number)) {
                    Predicate numberPredicate = criteriaBuilder.like(root.get("numero"), "%" + number + "%");
                    predicates.add(numberPredicate);
                }

                // Referencia.
                if (StringUtils.isNotBlank(reference)) {
                    Predicate numberPredicate = criteriaBuilder.like(root.get("referencia"), "%" + reference + "%");
                    predicates.add(numberPredicate);
                }

                // Descripcion.
                if (StringUtils.isNotBlank(description)) {
                    Predicate numberPredicate = criteriaBuilder.like(root.get("descripcion"), "%" + description + "%");
                    predicates.add(numberPredicate);
                }

                // Estado.
                if (StringUtils.isNotBlank(status)) {
                    Predicate statusPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("estado")), "%" + status.toLowerCase() + "%");
                    predicates.add(statusPredicate);
                }
                
                // Usuario caratulador.
        		if (StringUtils.isNotBlank(caratulador)) {
        			Expression<String> name = root.get("usuario").get("nombre").as(String.class);
        			Expression<String> lastName = root.get("usuario").get("apellido").as(String.class);

        			Predicate userNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(name),
        					"%" + caratulador.toLowerCase() + "%");
        			Predicate userLastNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(lastName),
        					"%" + caratulador.toLowerCase() + "%");
        			Predicate finalPredicate = criteriaBuilder.or(userNamePredicate, userLastNamePredicate);
        			
        			predicates.add(finalPredicate);
        		}
        		
        		// Identificador de usuario caratulador
        		
        		if (ObjectUtils.isNotEmpty(caratuladorId)) {
        			Predicate caratuladorIdPredicate = criteriaBuilder.equal(root.get("usuario").get("id"), caratuladorId);
        			predicates.add(caratuladorIdPredicate);
				}
        		

                // Filtro universal.
                if (StringUtils.isNotBlank(universalFilter)) {
                    Expression<String> id = root.get("id").as(String.class);
                    Expression<String> name = root.get("usuario").get("nombre").as(String.class);
        			Expression<String> lastName = root.get("usuario").get("apellido").as(String.class);
        			
                    Predicate universalPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("numero")), "%" + universalFilter.toLowerCase() + "%");

                    universalPredicate = criteriaBuilder.or(universalPredicate, criteriaBuilder.like(id, "%" + universalFilter.toLowerCase() + "%"));
                    universalPredicate = criteriaBuilder.or(universalPredicate, criteriaBuilder.like(name, "%" + universalFilter.toLowerCase() + "%"));
                    universalPredicate = criteriaBuilder.or(universalPredicate, criteriaBuilder.like(lastName, "%" + universalFilter.toLowerCase() + "%"));
                    universalPredicate = criteriaBuilder.or(universalPredicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("estado")), "%" + universalFilter.toLowerCase() + "%"));
                    universalPredicate = criteriaBuilder.or(universalPredicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("referencia")), "%" + universalFilter.toLowerCase() + "%"));
                    universalPredicate = criteriaBuilder.or(universalPredicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("descripcion")), "%" + universalFilter.toLowerCase() + "%"));

                    predicates.add(universalPredicate);
                }

                // Ordenamos
                if (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(orientation)) {

                    cq.orderBy(orientation.equals("asc")
                            ? criteriaBuilder.asc(root.get(orderBy))
                            : criteriaBuilder.desc(root.get(orderBy)));

                } else {
                    cq.orderBy(criteriaBuilder.asc(root.get("descripcion")));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }

        }, pageRequest);

        if (includeDocuments==false){
            expedientePage.getContent().forEach(item -> item.setDocumentos(Collections.emptyList()));
        }

        List<ExpedienteDTO> expedientes = this.expedienteMapper.toListDTO(expedientePage.getContent());

        WrapperData<ExpedienteDTO> wrapperData = new WrapperData<>();

        wrapperData.setItems(expedientes);
        wrapperData.setTotalItems(expedientePage.getTotalElements());
        wrapperData.setTotalPages(expedientePage.getTotalPages());
        wrapperData.setCurrentPage(expedientePage.getNumber());

        return wrapperData;

    }

    @Override
    public ExpedienteDTO findById(Long id, boolean includeDocuments) {
        Expediente expediente = this.expedienteRepository.findById(id).orElse(null);
        List<DocumentoDTO> documents = new ArrayList<>();

        if (ObjectUtils.isEmpty(expediente)) {
            throw MunicipalidadMQRuntimeException.notFoundException("No se encontro expediente");
        }

        if (includeDocuments && CollectionUtils.isNotEmpty(expediente.getDocumentos())) {
            int size = expediente.getDocumentos().size();
            documents = this.documentoMapper.toListDTO(expediente.getDocumentos());

            documents.forEach(item -> {
                String name = StringUtils.isBlank(item.getNombre()) ? "" : item.getNombre();
                item.setUrl(apiUrl.concat("/documentos/").concat(name));
            });
        }

        ExpedienteDTO expedienteDTO = this.expedienteMapper.toDTO(expediente);
        expedienteDTO.setDocumentos(documents);

        return expedienteDTO;
    }

    @Override
    public void updateStatus(Long id, String status) {
        this.expedienteRepository.updateStatus(id, status);
    }

	@Override
	public List<ExpedienteDTO> buscarTodosMisExpedientes(Long usuarioReceptorId) {
		PageRequest pageRequest = PageRequest.of(0, 1000);

		Page<Pase> expedientePage = paseRepository.findAll(new Specification<Pase>() {

			@Override
			public Predicate toPredicate(Root<Pase> root, CriteriaQuery<?> cq, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();

				// StartDate.
				if (ObjectUtils.isNotEmpty(usuarioReceptorId)) {
					Predicate startDatePredicate = criteriaBuilder.equal(root.get("usuarioReceptor").get("id"),
							usuarioReceptorId);
					predicates.add(startDatePredicate);
				}

				cq.orderBy(criteriaBuilder.asc(root.get("id")));

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}

		}, pageRequest);

		List<Pase> pases = expedientePage.getContent();
		
		List<ExpedienteDTO> lista = new ArrayList<ExpedienteDTO>();
		
		for (Pase item : pases) {
			lista.add(ExpedienteDTO.builder()
					.id(item.getExpediente().getId())
					.iniciador(item.getExpediente().getIniciador())
					.numero(item.getExpediente().getNumero())
					.referencia(item.getExpediente().getReferencia())
					.fechaCaratulacion(item.getExpediente().getFechaCaratulacion())
					.descripcion(item.getExpediente().getDescripcion())
					.codigoTramite(item.getExpediente().getCodigoTramite())
					.tipo(item.getExpediente().getTipo())
					.cantidadFojas(item.getExpediente().getCantidadFojas())
					.monto(item.getExpediente().getMonto())
					.estado(item.getExpediente().getEstado())
					.ultimaActualizacion(item.getExpediente().getUltimaActualizacion())
					.usuario(UsuarioDTO.builder()
							.id(item.getUsuarioReceptor().getId())
							.nombre(item.getUsuarioReceptor().getNombre())
							.apellido(item.getUsuarioReceptor().getApellido())
							.build())
					.usuarioEmisor(UsuarioBaseDTO.builder()
							.id(item.getUsuarioEmisor().getId())
							.nombre(item.getUsuarioEmisor().getNombre())
							.apellido(item.getUsuarioEmisor().getApellido())
							.build())
					.build());
		}
		
		// Agregar documentos
		
		
		for (ExpedienteDTO expedienteDTO : lista) {
			List<DocumentoDTO> documentos = new ArrayList<DocumentoDTO>();
			for (Pase item : pases) {
				if (expedienteDTO.getId().equals(item.getExpediente().getId())) {
					for (Documento docs : item.getExpediente().getDocumentos()) {
						DocumentoDTO documento = new DocumentoDTO();
						documento.setId(docs.getId());
						documento.setFechaSubida(docs.getFechaSubida());
						documento.setUbicacion(docs.getUbicacion());
						documento.setNombre(docs.getNombre());
						documento.setTipoArchivo(docs.getTipoArchivo());
						
						documento.setExpedienteId(docs.getExpediente().getId());
						
						documentos.add(documento);
					}
				}
			}
			expedienteDTO.setDocumentos(documentos);
		}

		return lista;
	

	}
}
