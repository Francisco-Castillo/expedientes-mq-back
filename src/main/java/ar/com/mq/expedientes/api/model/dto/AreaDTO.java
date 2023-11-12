package ar.com.mq.expedientes.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AreaDTO {

    private Long id;
    private String descripcion;
    private Long referenciaId;
    private Integer nivel;
    private String codigoPresupuestario;
}
