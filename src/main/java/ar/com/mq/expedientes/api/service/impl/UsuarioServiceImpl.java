package ar.com.mq.expedientes.api.service.impl;

import ar.com.mq.expedientes.api.model.dto.ExpedienteDTO;
import ar.com.mq.expedientes.api.model.dto.UsuarioBaseDTO;
import ar.com.mq.expedientes.api.model.dto.UsuarioDTO;
import ar.com.mq.expedientes.api.model.dto.WrapperData;
import ar.com.mq.expedientes.api.model.entity.Expediente;
import ar.com.mq.expedientes.api.model.entity.Usuario;
import ar.com.mq.expedientes.api.model.mapper.interfaces.UsuarioMapper;
import ar.com.mq.expedientes.api.service.interfaces.UsuarioService;
import ar.com.mq.expedientes.api.service.repository.UsuarioRepository;
import ar.com.mq.expedientes.core.security.model.dto.UserDetailsImpl;
import ar.com.mq.expedientes.core.utils.ZonedUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public void create(UsuarioDTO usuarioDTO) {
        usuarioDTO.setUuid(UUID.randomUUID().toString());
        usuarioDTO.setFechaAlta(LocalDateTime.now(ZonedUtils.ARGENTINA()));
        Usuario toEntity = this.usuarioMapper.toEntity(usuarioDTO);
        this.usuarioRepository.save(toEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = this.usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontro al usuario con el email " + username));

        UsuarioDTO usuarioDTO = this.usuarioMapper.toDTO(usuario);

        UserDetailsImpl userDetails = new UserDetailsImpl(usuarioDTO);

        return userDetails;
    }

    @Override
    public UsuarioDTO getUserInfo(String username) {
        Usuario usuario = this.usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontro al usuario con el email " + username));

        return UsuarioDTO.builder()
                .id(usuario.getId())
                .email(usuario.getEmail())
                .build();

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
}
