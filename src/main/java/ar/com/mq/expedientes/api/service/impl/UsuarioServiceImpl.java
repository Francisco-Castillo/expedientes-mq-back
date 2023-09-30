package ar.com.mq.expedientes.api.service.impl;

import ar.com.mq.expedientes.api.model.dto.UsuarioDTO;
import ar.com.mq.expedientes.api.model.entity.Usuario;
import ar.com.mq.expedientes.api.model.mapper.interfaces.UsuarioMapper;
import ar.com.mq.expedientes.api.service.interfaces.UsuarioService;
import ar.com.mq.expedientes.api.service.repository.UsuarioRepository;
import ar.com.mq.expedientes.core.security.model.dto.UserDetailsImpl;
import ar.com.mq.expedientes.core.utils.ZonedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
}
