package ar.com.mq.expedientes.api.model.dto;

import ar.com.mq.expedientes.core.business.bean.MunicipalidadMQDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ExpedienteDTO extends MunicipalidadMQDTO {

    private Long id;

    private String numero;

    private String referencia;

    private LocalDateTime fechaCaratulacion;

    private String descripcion;

    private String codigoTramite;

    private String tipo;

    private int cantidadFojas;

    private BigDecimal monto;

    private String estado;

    private LocalDateTime ultimaActualizacion;
}
