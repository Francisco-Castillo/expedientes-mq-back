package ar.com.mq.expedientes.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Long id;

    private String nombre;

    private String apellido;

    private String documento;

    private Integer estado;

    private Integer primerLogin; //0 - Si 1- NO

    private String email;

    private String password;

    private String uuid;

    private LocalDateTime fechaAlta;

    private AreaDTO area;
}
