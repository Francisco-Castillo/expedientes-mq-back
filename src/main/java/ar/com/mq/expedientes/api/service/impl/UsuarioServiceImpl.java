package ar.com.mq.expedientes.api.service.impl;

import ar.com.mq.expedientes.api.model.dto.UsuarioBaseDTO;
import ar.com.mq.expedientes.api.model.dto.UsuarioDTO;
import ar.com.mq.expedientes.api.model.dto.WrapperData;
import ar.com.mq.expedientes.api.model.entity.Usuario;
import ar.com.mq.expedientes.api.model.mapper.interfaces.UsuarioMapper;
import ar.com.mq.expedientes.api.service.interfaces.UsuarioService;
import ar.com.mq.expedientes.api.service.repository.UsuarioRepository;
import ar.com.mq.expedientes.core.exception.exceptions.MunicipalidadMQRuntimeException;
import ar.com.mq.expedientes.core.utils.PasswordUtils;
import ar.com.mq.expedientes.core.utils.ZonedUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
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
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    private static final Integer ACTIVO = 1;
    private static final Integer NO = 0;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public void create(UsuarioDTO usuarioDTO) {

        Optional<Usuario> usuario = this.usuarioRepository.findByEmail(usuarioDTO.getEmail());

        if (usuario.isPresent()) {
            throw MunicipalidadMQRuntimeException.conflictException("Ya existe un usuario registrado con el correo " + usuarioDTO.getEmail());
        }

        usuarioDTO.setUuid(UUID.randomUUID().toString());
        usuarioDTO.setFechaAlta(LocalDateTime.now(ZonedUtils.ARGENTINA()));
        usuarioDTO.setEstado(ACTIVO);
        usuarioDTO.setPrimerLogin(NO);
        Usuario toEntity = this.usuarioMapper.toEntity(usuarioDTO);

        this.usuarioRepository.save(toEntity);
    }

    @Override
    public UsuarioBaseDTO getUserInfo(String username) {

        Optional<Usuario> user = this.usuarioRepository.findByEmail(username);

        if (user.isEmpty()){
            throw MunicipalidadMQRuntimeException.notFoundException("No se encontro usuario");
        }

        return this.usuarioMapper.toBaseDTO(user.get());
    }

    @Override
    public WrapperData findAll(int page, int size, String search, String orderBy, String orientation) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Usuario> usuarioPage = usuarioRepository.findAll(new Specification<Usuario>() {

            @Override
            public Predicate toPredicate(Root<Usuario> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (StringUtils.isNotBlank(search)) {
                    predicates.add(cb.or(
                            cb.like(cb
                                    .lower(root.get("email")), "%" + search.toLowerCase() + "%"))
                    );
                }


                // Ordenamos
                if (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(orientation)) {

                    cq.orderBy(orientation.equals("asc")
                            ? cb.asc(root.get(orderBy))
                            : cb.desc(root.get(orderBy)));

                } else {
                    cq.orderBy(cb.asc(root.get("email")));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }

        }, pageRequest);

        List<UsuarioBaseDTO> usuarios = this.usuarioMapper.toListBaseDTO(usuarioPage.getContent());

        WrapperData<UsuarioBaseDTO> wrapperData = new WrapperData<>();

        wrapperData.setItems(usuarios);
        wrapperData.setTotalItems(usuarioPage.getTotalElements());
        wrapperData.setTotalPages(usuarioPage.getTotalPages());
        wrapperData.setCurrentPage(usuarioPage.getNumber());

        return wrapperData;

    }

    @Override
    public Usuario findByEmail(String email) {

        Usuario usuario = this.usuarioRepository.findByEmail(email).orElse(null);

        if (ObjectUtils.isEmpty(usuario)) {
            throw MunicipalidadMQRuntimeException.notFoundException("No se encontro usuario");
        }

        return usuario;
    }

    @Override
    public void changePassword(String email, String password) {
        try {
        	Usuario user = this.findByEmail(email);

            if (user.getEstado() == 0) {
                throw MunicipalidadMQRuntimeException.conflictException("Usuario inactivo");
            }

            if (user.getPassword().equals(PasswordUtils.encriptar(password))){
                throw MunicipalidadMQRuntimeException.badRequestException("La nueva contraseña debe ser diferente a la actual");
            }

            this.usuarioRepository.changePassword(user.getId(), PasswordUtils.encriptar(password));

        } catch (Exception e) {
            log.error("Ocurrio un error al intentar actualizar password para el usuario {}. Excepcion: {} ", email, e.getLocalizedMessage());
            throw MunicipalidadMQRuntimeException.conflictException(e.getLocalizedMessage());
        }
    }

    @Override
    public void changeStatus(Long userId, Integer status) {
        try {
            Optional<Usuario> user = this.usuarioRepository.findById(userId);

            if (user.isEmpty()) {
                throw MunicipalidadMQRuntimeException.conflictException("No se encontro usuario");
            }

            if (Objects.equals(user.get().getEstado(), status)) {
                throw MunicipalidadMQRuntimeException.conflictException("No se puede actualizar al mismo estado.");
            }

            this.usuarioRepository.changeStatus(userId, status);

        }catch (Exception e){
            log.error("Ocurrio un error al intentar actualizar estado al usuario {}. Excepcion: {}", userId,e.getLocalizedMessage());
            throw MunicipalidadMQRuntimeException.conflictException(e.getLocalizedMessage());
        }
    }
}
