package ar.com.mq.expedientes.core.utils;

import ar.com.mq.expedientes.api.model.dto.UsuarioDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {

    private final static String ACCESS_TOKEN_SECRET = "qCeFmqkwdnjgxcacsadqWWFCSAASDAsdasASD8789123";

    public static String create(Long id, String email) {

        long minutosExpiracion = 60L;

        LocalDateTime periodoExpiracion = LocalDateTime.now().plusMinutes(minutosExpiracion);

        Date horaExpiracion = Date.from(periodoExpiracion.atZone(ZoneId.systemDefault()).toInstant());

        Map<String, Object> extra = new HashMap<>();
        extra.put("userId", id);
        extra.put("email", email);

        return Jwts.builder()
                .setSubject(email)
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                .setIssuedAt(new Date())
                .setExpiration(horaExpiracion)
                .addClaims(extra)
                .compact();

    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
        try {

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();

            Integer userId = (Integer) claims.get("userId");
            Integer unidadEjecutoraId = (Integer) claims.get("unidadEjecutoraId");

            UsuarioDTO usuario = UsuarioDTO.builder()
                    .id(userId.longValue())
                    .email(email)
                    .build();

            return new UsernamePasswordAuthenticationToken(usuario, null, Collections.emptyList());

        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | SignatureException | IllegalArgumentException e) {
            return null;
        }
    }

}
