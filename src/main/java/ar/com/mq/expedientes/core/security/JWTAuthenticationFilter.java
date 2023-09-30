package ar.com.mq.expedientes.core.security;

import ar.com.mq.expedientes.api.model.dto.UsuarioDTO;
import ar.com.mq.expedientes.core.security.model.dto.UserDetailsImpl;
import ar.com.mq.expedientes.core.utils.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UsuarioDTO usuarioDTO = new ObjectMapper().readValue(request.getReader(), UsuarioDTO.class);

            UsernamePasswordAuthenticationToken usuernamePAT = new UsernamePasswordAuthenticationToken(
                    usuarioDTO.getEmail(),
                    usuarioDTO.getPassword(),
                    Collections.emptyList());

            return getAuthenticationManager().authenticate(usuernamePAT);

        } catch (IOException | AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        UserDetailsImpl usuario = (UserDetailsImpl) authResult.getPrincipal();

        String token = TokenUtils.create(usuario.getId(), usuario.getUsername());

        response.addHeader("Authorization", "Bearer " + token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);

    }
}
