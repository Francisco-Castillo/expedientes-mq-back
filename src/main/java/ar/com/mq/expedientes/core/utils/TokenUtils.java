package ar.com.mq.expedientes.core.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ar.com.mq.expedientes.api.model.dto.TokenDataDTO;

public class TokenUtils {

    private final static String ACCESS_TOKEN_SECRET = "qCeFmqkwdnjgxcacsadqWWFCSAASDAsdasASD8789123";

    public static String create(TokenDataDTO tokenData) {

        long minutosExpiracion = 60L;

        LocalDateTime periodoExpiracion = LocalDateTime.now().plusMinutes(minutosExpiracion);

        Date horaExpiracion = Date.from(periodoExpiracion.atZone(ZoneId.systemDefault()).toInstant());

        Map<String, Object> extra = new HashMap<>();
        extra.put("userId", tokenData.getUserId());
        extra.put("email", tokenData.getEmail());
        extra.put("name", tokenData.getName());
        extra.put("lastName", tokenData.getLastName());
        extra.put("areaId", tokenData.getAreaId());
        extra.put("areaName", tokenData.getAreaName());
        
        return Jwts.builder()
                .setSubject(tokenData.getEmail())
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                .setIssuedAt(new Date())
                .setExpiration(horaExpiracion)
                .addClaims(extra)
                .compact();

    }

    /*
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
    }*/

}
