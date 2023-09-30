package ar.com.mq.expedientes.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParametroDTO {

    private Long id;

    private String nombre;

    private String valor;

    private String descripcion;

}
